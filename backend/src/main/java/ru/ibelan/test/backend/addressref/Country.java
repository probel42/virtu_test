package ru.ibelan.test.backend.addressref;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Country extends NamedLocation {

    private List<Subject> subjects = new ArrayList<>();

    public Subject getSubject(String name) {
        return NamedLocation.findLocationByName(subjects, name);
    }
}
