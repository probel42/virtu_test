package ru.ibelan.test.backend.services.impl;

import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import ru.ibelan.test.backend.services.ApplicationDataService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

@Component
public class ApplicationDataServiceImpl implements ApplicationDataService {
    public static final String DATA_LOCATION = "insurancedata/insurance_data.json";

    enum InsuranceDataType {
        REAL_PROPERTY_TYPE_COEFFICIENT("realPropertyTypeCoefficient"),
        YEAR_COEFFICIENT("yearCoefficient"),
        AREA_COEFFICIENT("areaCoefficient");

        private final String name;

        InsuranceDataType(String name) {
            this.name = name;
        }
    }

    @Override
    public List<String> getRealPropertyTypes() throws IOException {
        List<Map<String, Object>> data = getData(InsuranceDataType.REAL_PROPERTY_TYPE_COEFFICIENT);
        return data.stream().map(s -> s.get("type")).map(Object::toString).collect(Collectors.toList());
    }

    @Override
    public float getRealPropertyTypeCoefficient(String type) throws IOException {
        List<Map<String, Object>> data = getData(InsuranceDataType.REAL_PROPERTY_TYPE_COEFFICIENT);
        return data.stream().filter(s -> type.equals(s.get("type"))).findFirst()
                .map(s -> (Double) s.get("coefficient")).map(Double::floatValue)
                .orElseThrow(() -> new RuntimeException(String.format("Не найден тип \"%s\"", type)));
    }

    @Override
    public float getYearCoefficient(int year) throws IOException {
        List<Map<String, Object>> data = getData(InsuranceDataType.YEAR_COEFFICIENT);

        Comparator<Map<String, Object>> comparator =
                Comparator.comparing(s -> (Integer) s.get("beforeYear"),
                        Comparator.nullsLast(Integer::compareTo).reversed());

        BinaryOperator<Map<String, Object>> reducer =
                (a, c) -> c.get("beforeYear") == null || year < ((Integer) c.get("beforeYear")) ? c : a;

        return data.stream().sorted(comparator).reduce(reducer)
                .map(d -> (Double) d.get("coefficient")).map(Double::floatValue).orElse(0F);
    }

    @Override
    public float getAreaCoefficient(int area) throws IOException {
        List<Map<String, Object>> data = getData(InsuranceDataType.AREA_COEFFICIENT);

        Comparator<Map<String, Object>> comparator =
                Comparator.comparing(s -> ((Integer) s.get("lessThan")),
                        Comparator.nullsLast(Integer::compareTo).reversed());

        BinaryOperator<Map<String, Object>> reducer =
                (a, c) -> c.get("lessThan") == null || area < ((Integer) c.get("lessThan")) ? c : a;

        return data.stream().sorted(comparator).reduce(reducer)
                .map(d -> ((Double) d.get("coefficient"))).map(Double::floatValue).orElse(0F);
    }

    private List<Map<String, Object>> getData(InsuranceDataType dataType) throws IOException {
        JsonParser jsonParser = new JacksonJsonParser();
        String json = new String(getResource(DATA_LOCATION).getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        Map<String, Object> data = jsonParser.parseMap(json);
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> result = (List<Map<String, Object>>) data.get(dataType.name);
        return result;
    }

    private Resource getResource(String name) {
        return new ClassPathResource(name);
    }
}
