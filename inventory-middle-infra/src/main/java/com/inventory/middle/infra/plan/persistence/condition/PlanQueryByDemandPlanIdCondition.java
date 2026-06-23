package com.inventory.middle.infra.plan.persistence.condition;

import lombok.Data;

import java.io.Serializable;

@Data
public class PlanQueryByDemandPlanIdCondition implements Serializable {

    private static final long serialVersionUID = 8071592065281931437L;

    private String tenantId;

    private Long demandPlanId;
}
