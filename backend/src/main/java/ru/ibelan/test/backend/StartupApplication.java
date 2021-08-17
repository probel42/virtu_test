package ru.ibelan.test.backend;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.ibelan.test.backend.services.PersonSearchService;

@Component
public class StartupApplication {

    private final PersonSearchService personSearchService;

    public StartupApplication(PersonSearchService personSearchService) {
        this.personSearchService = personSearchService;
    }

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        personSearchService.load();
    }
}
