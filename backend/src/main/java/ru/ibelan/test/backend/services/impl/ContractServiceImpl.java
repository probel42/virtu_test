package ru.ibelan.test.backend.services.impl;

import org.apache.commons.math3.util.Precision;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.ibelan.test.backend.entities.Address;
import ru.ibelan.test.backend.entities.Contract;
import ru.ibelan.test.backend.entities.Person;
import ru.ibelan.test.backend.entities.RealProperty;
import ru.ibelan.test.backend.graphql.input.AddressInput;
import ru.ibelan.test.backend.graphql.input.ContractInput;
import ru.ibelan.test.backend.graphql.input.RealPropertyInput;
import ru.ibelan.test.backend.repos.AddressRepository;
import ru.ibelan.test.backend.repos.ContractRepository;
import ru.ibelan.test.backend.repos.PersonRepository;
import ru.ibelan.test.backend.repos.RealPropertyRepository;
import ru.ibelan.test.backend.services.AddressRefService;
import ru.ibelan.test.backend.services.ApplicationDataService;
import ru.ibelan.test.backend.services.ContractService;

import javax.transaction.Transactional;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class ContractServiceImpl implements ContractService {
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private final ContractRepository contractRepository;
    private final AddressRepository addressRepository;
    private final RealPropertyRepository realPropertyRepository;
    private final PersonRepository personRepository;
    private final AddressRefService addressRefService;
    private final ApplicationDataService applicationDataService;

    public ContractServiceImpl(ContractRepository contractRepository,
                               AddressRepository addressRepository,
                               RealPropertyRepository realPropertyRepository,
                               PersonRepository personRepository,
                               AddressRefService addressRefService,
                               ApplicationDataService applicationDataService) {
        this.contractRepository = contractRepository;
        this.addressRepository = addressRepository;
        this.realPropertyRepository = realPropertyRepository;
        this.personRepository = personRepository;
        this.addressRefService = addressRefService;
        this.applicationDataService = applicationDataService;
    }

    @Override
    public long getContractsNumber() {
        return contractRepository.count();
    }

    @Override
    public List<Contract> getContracts(int page, int size) {
        return contractRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    @Override
    public Contract getContract(String id) {
        return contractRepository.findById(UUID.fromString(id)).orElse(null);
    }

    @Override
    @Transactional
    public String addEditContract(ContractInput contractInput) throws CalcInsuranceException, ParseException, IOException {
        RealPropertyInput realPropertyInput = contractInput.getRealProperty();
        AddressInput addressInput = realPropertyInput.getAddress();

        // address
        String country = addressInput.getCountry();
        String subject = addressInput.getSubject();
        String district = addressInput.getDistrict();
        String settlement = addressInput.getSettlement();
        String street = addressInput.getStreet();
        String plot = addressInput.getPlot();
        String building = addressInput.getBuilding();
        String housing = addressInput.getHousing();
        String apartment = addressInput.getApartment();

        // real property
        String realPropertyType = realPropertyInput.getType();
        Short year = realPropertyInput.getYear();
        Short area = realPropertyInput.getArea();

        // person
        String personId = contractInput.getInsuredId();

        Address address = addressRepository.findByParameters(country, subject, district, settlement, street,
                plot, building, housing, apartment).stream().findFirst()
                .orElse(createAddress(addressInput));

        RealProperty realProperty = realPropertyRepository.findByParameters(address).stream().findFirst()
                .orElse(createRealProperty(realPropertyInput, address));
        // todo notice: тут небольшая аналитическая ошибка: наличие типа "Комната" говорит о том,
        //  что в одной квартире может быть несколько площадей для страхования (именно поэтому сущность "недвижимость"
        //  отделена от сущности "адрес"), но для этого нужны какие-то дополнительные идентификаторы

        Person person = personRepository.findById(UUID.fromString(personId))
                .orElseThrow(() -> new CalcInsuranceException("Не найдена указанная в договоре персона."));

        // пересчитаем премию заново (на случай если коэффициенты расчёта были изменены)
        Float insurancePremium = calcInsurancePremium(contractInput.getInsuranceAmount(), realPropertyType,
                year.intValue(), area.intValue(), contractInput.getDateFrom(), contractInput.getDateTo());
        if (!Objects.equals(insurancePremium, contractInput.getCalcPremium())) {
            throw new CalcInsuranceException("Необходим перерасчёт страховой премии");
        }

        Contract contract = createOrUpdateContract(contractInput, realProperty, person);

        // докинем адрес в справочник в монго
        addressRefService.saveAddress(country, subject, district, settlement, street);

        return Objects.requireNonNull(contract.getId()).toString();
    }

    public Address createAddress(AddressInput addressInput) {
        Address address = new Address();
        address.setZipCode(addressInput.getZipCode());
        address.setCountry(addressInput.getCountry());
        address.setSubject(addressInput.getSubject());
        address.setDistrict(addressInput.getDistrict());
        address.setSettlement(addressInput.getSettlement());
        address.setStreet(addressInput.getStreet());
        address.setPlot(addressInput.getPlot());
        address.setBuilding(addressInput.getBuilding());
        address.setHousing(addressInput.getHousing());
        address.setApartment(addressInput.getApartment());
        return addressRepository.saveAndFlush(address);
    }

    public RealProperty createRealProperty(RealPropertyInput realPropertyInput, Address address) {
        RealProperty realProperty = new RealProperty();
        realProperty.setAddress(address);
        realProperty.setType(realPropertyInput.getType());
        realProperty.setYear(realPropertyInput.getYear());
        realProperty.setArea(realPropertyInput.getArea());
        return realPropertyRepository.saveAndFlush(realProperty);
    }

    public Contract createOrUpdateContract(ContractInput contractInput, RealProperty realProperty, Person person)
            throws ParseException {
        Contract contract = Optional.ofNullable(contractInput.getId()).map(this::getContract).orElse(new Contract());
        contract.setNumber(contractInput.getNumber());
        contract.setSignDate(DATE_FORMAT.parse(contractInput.getSignDate()));
        contract.setInsured(person);
        contract.setRealProperty(realProperty);
        contract.setInsuranceAmount(contractInput.getInsuranceAmount());
        contract.setDateFrom(DATE_FORMAT.parse(contractInput.getDateFrom()));
        contract.setDateTo(DATE_FORMAT.parse(contractInput.getDateTo()));
        contract.setCalcDate(DATE_FORMAT.parse(contractInput.getCalcDate()));
        contract.setCalcPremium((long) Math.round(contractInput.getCalcPremium() * 100));
        contract.setComment(contractInput.getComment());
        return contractRepository.saveAndFlush(contract);
    }

    @Override
    public List<String> getRealPropertyTypes() throws IOException {
        return applicationDataService.getRealPropertyTypes();
    }

    @Override
    public Float calcInsurancePremium(int insuranceAmount,
                                      String realPropertyType,
                                      Integer year,
                                      Integer area,
                                      String dateFrom,
                                      String dateTo) throws ParseException, IOException {
        LocalDate startDate = DATE_FORMAT.parse(dateFrom).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = DATE_FORMAT.parse(dateTo).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        long period = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        float typeCoefficient = applicationDataService.getRealPropertyTypeCoefficient(realPropertyType);
        float yearCoefficient = applicationDataService.getYearCoefficient(year);
        float areaCoefficient = applicationDataService.getAreaCoefficient(area);

        float result = (insuranceAmount * typeCoefficient * yearCoefficient * areaCoefficient) / period;
        return Precision.round(result, 2);
    }
}
