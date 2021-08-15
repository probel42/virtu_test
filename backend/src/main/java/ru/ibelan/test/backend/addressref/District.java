package ru.ibelan.test.backend.addressref;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class District extends NamedLocation {

    private List<Settlement> settlements = new ArrayList<>();

    public Settlement getSettlement(String name) {
        return NamedLocation.findLocationByName(settlements, name);
    }
}
