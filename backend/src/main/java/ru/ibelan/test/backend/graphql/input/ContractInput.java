package ru.ibelan.test.backend.graphql.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContractInput {
    private String id;
    private Integer number;
    private String signDate;
    private String insuredId;
    private RealPropertyInput realProperty;
    private Integer insuranceAmount;
    private String dateFrom;
    private String dateTo;
    private String calcDate;
    private Float calcPremium;
    private String comment;
}
