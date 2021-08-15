package ru.ibelan.test.backend.addressref;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Settlement extends NamedLocation {

    private List<Street> streets = new ArrayList<>();

    public Street getStreet(String name) {
        return NamedLocation.findLocationByName(streets, name);
    }
}
