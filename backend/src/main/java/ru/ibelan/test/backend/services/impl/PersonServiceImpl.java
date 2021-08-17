package ru.ibelan.test.backend.services.impl;

import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.stereotype.Service;
import ru.ibelan.test.backend.entities.Person;
import ru.ibelan.test.backend.graphql.input.PersonInput;
import ru.ibelan.test.backend.repos.PersonRepository;
import ru.ibelan.test.backend.services.PersonSearchService;
import ru.ibelan.test.backend.services.PersonService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final PersonSearchService personSearchService;

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    PersonServiceImpl(PersonRepository personRepository, PersonSearchService personSearchService) {
        this.personRepository = personRepository;
        this.personSearchService = personSearchService;
    }

    @Override
    public List<Person> getPersons(String search) {
        List<UUID> ids = personSearchService.search(search).stream().map(UUID::fromString)
                .collect(Collectors.toList());
        List<Person> result = personRepository.findAllById(ids);
        result.sort(Comparator.comparing(AbstractPersistable::getId, Comparator.comparingInt(ids::indexOf)));
        return result;
    }

    @Override
    public Person getPerson(String id) {
        return personRepository.findById(UUID.fromString(id)).orElse(null);
    }

    @Override
    public String addEditPerson(PersonInput personInput) throws ParseException {
        Person person = Optional.ofNullable(personInput.getId()).map(this::getPerson).orElse(new Person());
        person.setName(personInput.getName());
        person.setSurname(personInput.getSurname());
        person.setPatronymic(personInput.getPatronymic());
        person.setBirthDate(DATE_FORMAT.parse(personInput.getBirthDate()));
        person.setPassportSeries(personInput.getPassportSeries());
        person.setPassportNumber(personInput.getPassportNumber());
        Person result = personRepository.saveAndFlush(person);

        // докинем к lucene
        personSearchService.addPerson(person);

        return Objects.requireNonNull(result.getId()).toString();
    }
}
