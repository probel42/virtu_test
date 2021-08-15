package ru.ibelan.test.backend.services;

import ru.ibelan.test.backend.addressref.Country;

import java.util.List;

public interface AddressRefService {
    List<Country> getCountries();
    Country getCountry(String name);
    int saveAddress(String countryName,
                     String subjectName,
                     String districtName,
                     String settlementName,
                     String streetName);
}
