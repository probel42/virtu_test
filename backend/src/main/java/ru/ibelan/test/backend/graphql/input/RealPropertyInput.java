package ru.ibelan.test.backend.graphql.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RealPropertyInput {
    private AddressInput address;
    private String type;
    private String year;
    private Short area;
}
