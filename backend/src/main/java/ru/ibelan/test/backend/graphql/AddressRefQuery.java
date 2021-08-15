package ru.ibelan.test.backend.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;
import ru.ibelan.test.backend.addressref.Country;
import ru.ibelan.test.backend.services.AddressRefService;

import java.util.List;

@Component
public class AddressRefQuery implements GraphQLQueryResolver {

    private final AddressRefService addressRefService;

    public AddressRefQuery(AddressRefService addressRefService) {
        this.addressRefService = addressRefService;
    }

    public List<Country> getCountries() {
        return addressRefService.getCountries();
    }

    public Country getCountry(String name) {
        return addressRefService.getCountry(name);
    }
}
