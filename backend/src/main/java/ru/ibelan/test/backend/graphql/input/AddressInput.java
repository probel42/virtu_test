package ru.ibelan.test.backend.graphql.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressInput {
    private String zipCode;
    private String country;
    private String subject;
    private String district;
    private String settlement;
    private String street;
    private String plot;
    private String building;
    private String housing;
    private String apartment;
}
