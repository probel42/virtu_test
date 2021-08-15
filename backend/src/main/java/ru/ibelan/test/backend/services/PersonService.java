package ru.ibelan.test.backend.services;

import ru.ibelan.test.backend.entities.Person;
import ru.ibelan.test.backend.graphql.input.PersonInput;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;

public interface PersonService {
    List<Person> getPersons(String search);
    List<Person> getPersons(List<String> ids);
    Person getPerson(String id);
    String addEditPerson(PersonInput personInput) throws ParseException;
}
