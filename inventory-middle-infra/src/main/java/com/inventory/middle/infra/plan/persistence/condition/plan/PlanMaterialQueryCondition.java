package com.inventory.middle.infra.plan.persistence.condition.plan;

import lombok.Data;

@Data
public class PlanMaterialQueryCondition {
    private String materialCode;
    private String logicalPlantNo;
}
