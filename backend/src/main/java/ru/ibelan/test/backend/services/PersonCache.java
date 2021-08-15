package ru.ibelan.test.backend.services;

import ru.ibelan.test.backend.entities.Person;

import java.util.List;

public interface PersonCache {
    void load();
    void addPerson(Person person);
    List<String> search(String search);
}
