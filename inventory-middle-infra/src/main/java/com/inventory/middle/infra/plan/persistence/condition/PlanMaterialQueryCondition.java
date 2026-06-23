package com.inventory.middle.infra.plan.persistence.condition;

import lombok.Data;

@Data
public class PlanMaterialQueryCondition {
    private String materialCode;
    private String logicalPlantNo;
}
