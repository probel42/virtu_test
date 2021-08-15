package ru.ibelan.test.backend.services.impl;

import org.springframework.stereotype.Service;
import ru.ibelan.test.backend.addressref.*;
import ru.ibelan.test.backend.repos.AddressRefRepository;
import ru.ibelan.test.backend.services.AddressRefService;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class AddressRefServiceImpl implements AddressRefService {

    private final AddressRefRepository addressRefRepository;

    public AddressRefServiceImpl(AddressRefRepository addressRefRepository) {
        this.addressRefRepository = addressRefRepository;
    }

    @Override
    public List<Country> getCountries() {
        return getAddressRef().map(AddressRef::getCountries).orElse(Collections.emptyList());
    }

    @Override
    public Country getCountry(String name) {
        return getAddressRef().map(ar -> ar.getCountry(name)).orElse(null);
    }

    @Override
    public int saveAddress(String countryName,
                            String subjectName,
                            String districtName,
                            String settlementName,
                            String streetName) {
        AddressRef addressRef = getAddressRef().orElse(null);
        if (addressRef == null) {
            return -1;
        }

        try {
            Country country = getOrCreateLocation(addressRef.getCountries(), countryName, Country.class);
            Subject subject = getOrCreateLocation(country.getSubjects(), subjectName, Subject.class);
            Settlement settlement;
            if (districtName != null) {
                District district = getOrCreateLocation(subject.getDistricts(), districtName, District.class);
                settlement = getOrCreateLocation(district.getSettlements(), settlementName, Settlement.class);
            } else {
                settlement = getOrCreateLocation(subject.getSettlements(), settlementName, Settlement.class);
            }
            getOrCreateLocation(settlement.getStreets(), streetName, Street.class);
            addressRefRepository.save(addressRef);
            return 0;
        } catch (ReflectiveOperationException ignored) {
            return -1;
        }
    }

    private <T extends NamedLocation> T getOrCreateLocation(List<T> locations, String name, Class<T> clazz)
            throws ReflectiveOperationException {
        T location = NamedLocation.findLocationByName(locations, name);
        if (location == null) {
            location = clazz.getConstructor().newInstance();
            location.setName(name);
            locations.add(location);
            locations.sort(Comparator.comparing(NamedLocation::getName));
        }
        return location;
    }

    private Optional<AddressRef> getAddressRef() {
        // в базе должен быть один документ в коллекции "address_ref"
        return addressRefRepository.findAll().stream().findAny();
    }
}
