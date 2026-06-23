package com.inventory.middle.infra.plan.persistence.condition;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PlanMaterialParamPageCondition extends PageCondition {
    private String tenantId;
    private String materialCode;
    private String logicalPlantNo;
    private String operatorName;
}
