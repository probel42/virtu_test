package ru.ibelan.test.backend.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "contract")
@Getter
@Setter
public class Contract extends AbstractPersistable<UUID> {

    @Column(name = "number", nullable = false)
    private Integer number;

    @Column(name = "sign_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date signDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "insured_id", nullable = false)
    private Person insured;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "real_property_id", nullable = false)
    private RealProperty realProperty;

    @Column(name = "insurance_amount", nullable = false)
    private Integer insuranceAmount;

    @Column(name = "date_from", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateFrom;

    @Column(name = "date_to", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateTo;

    @Column(name = "calc_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date calcDate;

    @Column(name = "calc_premium", nullable = false)
    private Double calcPremium;

    @Column(name = "comment")
    private String comment;
}
