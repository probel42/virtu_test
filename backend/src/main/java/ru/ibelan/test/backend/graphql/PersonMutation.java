package ru.ibelan.test.backend.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.springframework.stereotype.Component;
import ru.ibelan.test.backend.graphql.input.PersonInput;
import ru.ibelan.test.backend.services.PersonService;

import java.text.ParseException;

@Component
public class PersonMutation implements GraphQLMutationResolver {

    private final PersonService personService;

    public PersonMutation(PersonService personService) {
        this.personService = personService;
    }

    public String addEditPerson(PersonInput person) throws ParseException {
        return personService.addEditPerson(person);
    }
}
