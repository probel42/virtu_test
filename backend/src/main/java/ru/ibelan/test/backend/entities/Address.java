package ru.ibelan.test.backend.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "address")
@Getter
@Setter
public class Address extends AbstractPersistable<UUID> {

    @Column(name = "zipcode")
    private String zipCode;

    @Column(name = "country")
    private String country;

    @Column(name = "subject")
    private String subject;

    @Column(name = "district")
    private String district;

    @Column(name = "settlement")
    private String settlement;

    @Column(name = "street")
    private String street;

    @Column(name = "plot", nullable = false)
    private String plot;

    @Column(name = "building")
    private String building;

    @Column(name = "housing")
    private String housing;

    @Column(name = "apartment")
    private String apartment;
}
