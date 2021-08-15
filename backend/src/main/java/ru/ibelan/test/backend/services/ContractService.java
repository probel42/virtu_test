package ru.ibelan.test.backend.services;

import ru.ibelan.test.backend.entities.Contract;
import ru.ibelan.test.backend.graphql.input.ContractInput;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface ContractService {
    long getContractsNumber();
    List<Contract> getContracts(int page, int size);
    Contract getContract(String id);
    String addEditContract(ContractInput contract) throws CalcInsuranceException, ParseException, IOException;
    Float calcInsurancePremium(int insuranceAmount,
                               String realPropertyType,
                               String year,
                               Integer area,
                               String dateFrom,
                               String dateTo) throws ParseException, IOException;

    List<String> getRealPropertyTypes() throws IOException;

    class CalcInsuranceException extends RuntimeException {
        public CalcInsuranceException(String message) {
            super(message);
        }
    }
}
