package ru.ibelan.test.backend.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.UUID;

/**
 * Объект недвижимости
 */
@Entity
@Table(name = "real_property")
@Getter
@Setter
public class RealProperty extends AbstractPersistable<UUID> {

    /**
     * Адрес объекта
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    /**
     * Тип недвижимости
     */
    @Column(name = "type", length = 50, nullable = false)
    private String type;

    /**
     * Год постройки
     */
    @Column(name = "construction_year", length = 4, nullable = false)
    private Short year;

    /**
     * Площадь, кв.м.
     */
    @Column(name = "area", nullable = false)
    private Short area;
}
