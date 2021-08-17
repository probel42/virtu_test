package ru.ibelan.test.backend.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;
import ru.ibelan.test.backend.entities.Contract;
import ru.ibelan.test.backend.services.ContractService;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@Component
public class ContractQuery implements GraphQLQueryResolver {

    private final ContractService contractService;

    public ContractQuery(ContractService contractService) {
        this.contractService = contractService;
    }

    public long getContractsNumber() {
        return contractService.getContractsNumber();
    }

    public List<Contract> getContracts(int page, int size) {
        return contractService.getContracts(page, size);
    }

    public Contract getContract(String id) {
        return contractService.getContract(id);
    }

    public List<String> getRealPropertyTypes() throws IOException {
        return contractService.getRealPropertyTypes();
    }

    public Long calcInsurancePremium(int insuranceAmount,
                                      String realPropertyType,
                                      Integer year,
                                      Integer area,
                                      String dateFrom,
                                      String dateTo) throws ParseException, IOException {
        return contractService.calcInsurancePremium(insuranceAmount, realPropertyType, year, area, dateFrom, dateTo);
    }
}
