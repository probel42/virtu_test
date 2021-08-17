package ru.ibelan.test.backend.addressref;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class NamedLocation {
    private String name;

    public static <T extends NamedLocation> T findLocationByName(List<T> locations, String name) {
        if (locations == null) {
            return null;
        }
        return locations.stream().filter(l -> Objects.equals(name, l.getName())).findFirst().orElse(null);
    }
}
