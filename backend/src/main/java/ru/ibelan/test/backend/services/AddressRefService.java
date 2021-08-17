package ru.ibelan.test.backend.services;

import ru.ibelan.test.backend.addressref.Country;

import java.util.List;

/**
 * Сервис для работы с адресами в Mongo
 */
public interface AddressRefService {

    /**
     * @return все страны
     */
    List<Country> getCountries();

    /**
     * @return страна по имени
     */
    Country getCountry(String name);

    /**
     * Сохранить адрес в mongo
     * @return -1 если ошибка (не выкидывает исключение, т.к. это необязательная операция)
     */
    int saveAddress(String countryName,
                     String subjectName,
                     String districtName,
                     String settlementName,
                     String streetName);
}
