package ru.ibelan.test.backend.addressref;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Subject extends NamedLocation {

    private List<District> districts = new ArrayList<>();
    private List<Settlement> settlements = new ArrayList<>();

    public District getDistrict(String name) {
        return NamedLocation.findLocationByName(districts, name);
    }

    public Settlement getSettlement(String name) {
        return NamedLocation.findLocationByName(settlements, name);
    }
}
