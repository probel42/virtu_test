package ru.ibelan.test.backend.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.text.ParseException;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
public class ContractServiceTest {
    @Autowired
    private ContractService contractService;

    @Autowired
    private ApplicationDataService applicationDataService;

    @Test
    void calcInsuranceTest() throws ParseException, IOException {
        int insuranceAmount = 10000;
        String realPropertyType = "Квартира";
        int year = 2000;
        int area = 50;
        // 367 дней
        String startDate = "2020-01-01";
        String endDate = "2021-01-01";

        // period
        int period = 367;
        // coefficients
        float typeCoefficient = applicationDataService.getRealPropertyTypeCoefficient(realPropertyType);
        float yearCoefficient = applicationDataService.getYearCoefficient(year);
        float areaCoefficient = applicationDataService.getAreaCoefficient(area);

        // expectation
        float expected = (insuranceAmount * typeCoefficient * yearCoefficient * areaCoefficient) / period;
        expected = (long) Math.round(expected * 100.0F);
        // reality
        float actual = contractService.calcInsurancePremium(insuranceAmount,
                realPropertyType, year, area, startDate, endDate);

        assertEquals("Ошибка в расчёте страховой премии", expected, actual);
    }
}
