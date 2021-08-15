package ru.ibelan.test.backend;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.ibelan.test.backend.services.PersonCache;

@Component
public class StartupApplication {
    private final PersonCache personCache;

    public StartupApplication(PersonCache personCache) {
        this.personCache = personCache;
    }

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        personCache.load();
    }
}
