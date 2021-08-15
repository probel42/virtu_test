package ru.ibelan.test.backend.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.ibelan.test.backend.entities.Address;
import ru.ibelan.test.backend.entities.RealProperty;

import java.util.List;
import java.util.UUID;

@Repository
public interface RealPropertyRepository extends JpaRepository<RealProperty, UUID> {

    @Query("SELECT rp FROM RealProperty rp WHERE rp.address = ?1")
    List<RealProperty> findByParameters(Address address);
}
