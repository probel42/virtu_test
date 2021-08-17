package ru.ibelan.test.backend.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * Договор
 */
@Entity
@Table(name = "contract")
@Getter
@Setter
public class Contract extends AbstractPersistable<UUID> {

    /**
     * Номер договора
     */
    @Column(name = "number", unique = true, nullable = false)
    private Integer number;

    /**
     * Дата заключения
     */
    @Column(name = "sign_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date signDate;

    /**
     * Страхователь
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "insured_id", nullable = false)
    private Person insured;

    /**
     * Объект недвижимости
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "real_property_id", nullable = false)
    private RealProperty realProperty;

    /**
     * Страховая сумма
     */
    @Column(name = "insurance_amount", nullable = false)
    private Integer insuranceAmount;

    /**
     * Дата действия с
     */
    @Column(name = "date_from", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateFrom;

    /**
     * Дата действия по
     */
    @Column(name = "date_to", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateTo;

    /**
     * Дата расчёта
     */
    @Column(name = "calc_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date calcDate;

    /**
     * Премия (умноженная на 100)
     */
    @Column(name = "calc_premium", nullable = false)
    private Long calcPremium;

    /**
     * Комментарий
     */
    @Column(name = "comment")
    private String comment;
}
