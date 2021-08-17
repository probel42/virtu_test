package ru.ibelan.test.backend.addressref;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Адресные данные будем также хранить в Mongo (для удобства поиска и минимизации ошибок ввода)
 */
@Document(collection = "address_ref")
@Getter
@Setter
@RequiredArgsConstructor
public class AddressRef {
    @Id
    private final String id;

    private List<Country> countries = new ArrayList<>();

    public Country getCountry(String name) {
        return NamedLocation.findLocationByName(countries, name);
    }
}
