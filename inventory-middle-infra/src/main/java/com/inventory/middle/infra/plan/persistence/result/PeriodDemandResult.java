package com.inventory.middle.infra.plan.persistence.result;

import lombok.Data;

@Data
public class PeriodDemandResult {

    private Long periodId;

    private String planPeriod;

    private Long planAmount;

    private String extInfo;
}
