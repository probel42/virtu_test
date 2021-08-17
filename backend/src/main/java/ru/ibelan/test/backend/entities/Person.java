package ru.ibelan.test.backend.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * Персона
 */
@Entity
@Table(name = "person")
@Getter
@Setter
public class Person extends AbstractPersistable<UUID> {

    /**
     * Имя
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Фамилия
     */
    @Column(name = "surname", nullable = false)
    private String surname;

    /**
     * Отчество
     */
    @Column(name = "patronymic")
    private String patronymic;

    /**
     * Дата рождения
     */
    @Column(name = "birth_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    /**
     * Серия пасспорта
     */
    @Column(name = "passport_series", length = 4, nullable = false)
    private String passportSeries;

    /**
     * Номер пасспорта
     */
    @Column(name = "passport_number", length = 6, nullable = false)
    private String passportNumber;
}
