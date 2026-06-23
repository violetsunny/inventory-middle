package com.inventory.middle.infra.plan.persistence.condition;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PlanOrderCondition {
    private String tenantId;
    private String materialCode;
    private String logicalPlantNo;
    private Date planReceivingTime;
    private List<Integer> statuses;
}
