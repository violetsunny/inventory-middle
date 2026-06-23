package com.inventory.middle.infra.plan.persistence.condition.plan;

import lombok.Data;

@Data
public class PlanMaterialParamQueryCondition {
    private String materialCode;
    private String logicalPlantNo;
    private String tenantId;
}
