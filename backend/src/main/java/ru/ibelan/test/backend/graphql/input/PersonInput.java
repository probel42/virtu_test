package ru.ibelan.test.backend.graphql.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonInput {
    private String id;
    private String name;
    private String surname;
    private String patronymic;
    private String birthDate;
    private String passportSeries;
    private String passportNumber;
}
