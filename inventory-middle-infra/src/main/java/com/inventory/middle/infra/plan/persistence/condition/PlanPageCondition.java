package com.inventory.middle.infra.plan.persistence.condition;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PlanPageCondition extends PageCondition {
    private String planCode;
    private String planDesc;
    private Integer planType;
    private String operatorName;
    private String tenantId;
    private Integer status;
}
