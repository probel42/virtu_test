package ru.ibelan.test.backend.services;

import java.io.IOException;
import java.util.List;

/**
 * Сервис для доступа к данными приложения (коэффициентам) в JSON
 */
public interface ApplicationDataService {

    /**
     * @return типы недвижимости
     */
    List<String> getRealPropertyTypes() throws IOException;

    /**
     * @return коэффициент по типу недвижимости
     */
    float getRealPropertyTypeCoefficient(String type) throws IOException;

    /**
     * @return коэффициент по году постройки
     */
    float getYearCoefficient(int year) throws IOException;

    /**
     * @return коэффициент по площади, кв.м.
     */
    float getAreaCoefficient(int area) throws IOException;
}
