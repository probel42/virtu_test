package ru.ibelan.test.backend.services;

import java.io.IOException;
import java.util.List;

public interface ApplicationDataService {
    List<String> getRealPropertyTypes() throws IOException;

    float getRealPropertyTypeCoefficient(String type) throws IOException;
    float getYearCoefficient(int year) throws IOException;
    float getAreaCoefficient(int area) throws IOException;
}
