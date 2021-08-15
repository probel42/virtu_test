package ru.ibelan.test.backend.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.ibelan.test.backend.entities.Address;

import java.util.List;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {

    @Query("SELECT a FROM Address a WHERE a.country = ?1 AND a.subject = ?2 AND a.district = ?3 AND a.settlement = ?4" +
            " AND a.street = ?5 AND a.plot = ?6 AND a.building = ?7 AND a.housing = ?8 AND a.apartment = ?9")
    List<Address> findByParameters(String country, String subject, String district, String settlement,
                                   String street, String plot, String building, String housing, String apartment);
}
