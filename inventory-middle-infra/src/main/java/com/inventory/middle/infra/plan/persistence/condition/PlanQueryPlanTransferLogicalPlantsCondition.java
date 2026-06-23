package com.inventory.middle.infra.plan.persistence.condition;

import lombok.Data;

@Data
public class PlanQueryPlanTransferLogicalPlantsCondition {
    private String materialCode;
    private String tenantId;
    private String logicalPlantNo;
}
