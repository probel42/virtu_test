package ru.ibelan.test.backend.services;

import ru.ibelan.test.backend.entities.Person;
import ru.ibelan.test.backend.graphql.input.PersonInput;

import java.text.ParseException;
import java.util.List;

/**
 * Сервис для работы с персонами
 */
public interface PersonService {

    /**
     * Поиск персон по строке поиска (использует PersonSearchService)
     * @return список персон отсортированный по релевантности персон
     */
    List<Person> getPersons(String search);

    /**
     * @return персона по идентификатору
     */
    Person getPerson(String id);

    /**
     * Добавить/изменить персону
     * @return id персоны
     */
    String addEditPerson(PersonInput personInput) throws ParseException;
}
