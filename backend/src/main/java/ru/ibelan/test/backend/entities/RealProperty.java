package ru.ibelan.test.backend.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "real_property")
@Getter
@Setter
public class RealProperty extends AbstractPersistable<UUID> {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @Column(name = "type", length = 50, nullable = false)
    private String type;

    @Column(name = "construction_year", length = 4, nullable = false)
    private String year;

    @Column(name = "area", nullable = false)
    private Short area;
}
