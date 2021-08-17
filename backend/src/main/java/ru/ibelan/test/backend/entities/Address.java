package ru.ibelan.test.backend.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

/**
 * Адрес недвижимости
 */
@Entity
@Table(name = "address")
@Getter
@Setter
public class Address extends AbstractPersistable<UUID> {

    /**
     * Индекс
     */
    @Column(name = "zipcode")
    private String zipCode;

    /**
     * Страна
     */
    @Column(name = "country", nullable = false)
    private String country;

    /**
     * Республика, край, область
     */
    @Column(name = "subject", nullable = false)
    private String subject;

    /**
     * Район
     */
    @Column(name = "district")
    private String district;

    /**
     * Населённый пункт
     */
    @Column(name = "settlement", nullable = false)
    private String settlement;

    /**
     * Улица
     */
    @Column(name = "street", nullable = false)
    private String street;

    /**
     * Дом
     */
    @Column(name = "plot", nullable = false)
    private String plot;

    /**
     * Корпус
     */
    @Column(name = "building")
    private String building;

    /**
     * Строение
     */
    @Column(name = "housing")
    private String housing;

    /**
     * Квартира
     */
    @Column(name = "apartment")
    private String apartment;
}
