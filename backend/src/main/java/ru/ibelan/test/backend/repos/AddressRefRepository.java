package ru.ibelan.test.backend.repos;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.ibelan.test.backend.addressref.AddressRef;

@Repository
public interface AddressRefRepository extends MongoRepository<AddressRef, String> {
}
