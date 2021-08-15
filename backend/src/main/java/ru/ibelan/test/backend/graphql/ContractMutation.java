package ru.ibelan.test.backend.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.springframework.stereotype.Component;
import ru.ibelan.test.backend.graphql.input.ContractInput;
import ru.ibelan.test.backend.services.ContractService;

import java.io.IOException;
import java.text.ParseException;

@Component
public class ContractMutation implements GraphQLMutationResolver {

    private final ContractService contractService;

    public ContractMutation(ContractService contractService) {
        this.contractService = contractService;
    }

    public String addEditContract(ContractInput contract) throws ParseException, IOException {
        return contractService.addEditContract(contract);
    }
}
