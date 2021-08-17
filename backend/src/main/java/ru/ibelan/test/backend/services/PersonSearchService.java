package ru.ibelan.test.backend.services;

import ru.ibelan.test.backend.entities.Person;

import java.util.List;

/**
 * Сервис для поиска персон
 */
public interface PersonSearchService {

    /**
     * Загрузить персоны из базы в lucene
     */
    void load();

    /**
     * Добавить персону в lucene
     */
    void addPerson(Person person);

    /**
     * Поиск персон по строке поиска
     * @return список идентификаторов персон отсортированный по релевантности персон
     */
    List<String> search(String search);
}
