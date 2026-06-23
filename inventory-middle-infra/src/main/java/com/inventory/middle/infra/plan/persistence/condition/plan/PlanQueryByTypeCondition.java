package com.inventory.middle.infra.plan.persistence.condition.plan;

import lombok.Data;

@Data
public class PlanQueryByTypeCondition {
    private String tenantId;
    private Integer planType;
}
