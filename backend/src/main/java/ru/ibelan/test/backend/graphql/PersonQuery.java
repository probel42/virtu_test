package ru.ibelan.test.backend.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;
import ru.ibelan.test.backend.entities.Person;
import ru.ibelan.test.backend.services.PersonService;

import java.util.List;

@Component
public class PersonQuery implements GraphQLQueryResolver {

    private final PersonService personService;

    PersonQuery(PersonService personService) {
        this.personService = personService;
    }

    public List<Person> getPersons(String search) {
        return personService.getPersons(search);
    }

    public Person getPerson(String id) {
        return personService.getPerson(id);
    }
}
