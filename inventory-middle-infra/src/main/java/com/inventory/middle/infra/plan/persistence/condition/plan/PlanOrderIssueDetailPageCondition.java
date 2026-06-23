package com.inventory.middle.infra.plan.persistence.condition.plan;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PlanOrderIssueDetailPageCondition extends PageCondition {
    private Long planOrderId;
    private String tenantId;
}
