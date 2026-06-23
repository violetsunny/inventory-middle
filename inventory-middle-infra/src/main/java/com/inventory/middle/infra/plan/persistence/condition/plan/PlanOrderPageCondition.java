package com.inventory.middle.infra.plan.persistence.condition.plan;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class PlanOrderPageCondition extends PageCondition {
    private String orderNo;
    private String materialCode;
    private String logicalPlantNo;
    private String tenantId;
    private List<Integer> statusList;
    private Date maxCreateTime;
    private Date minCreateTime;
}
