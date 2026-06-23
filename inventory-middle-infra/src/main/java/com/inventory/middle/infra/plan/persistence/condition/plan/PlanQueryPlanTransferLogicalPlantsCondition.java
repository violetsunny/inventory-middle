package com.inventory.middle.infra.plan.persistence.condition.plan;

import lombok.Data;

@Data
public class PlanQueryPlanTransferLogicalPlantsCondition {
    private String materialCode;
    private String tenantId;
    private String logicalPlantNo;
}
