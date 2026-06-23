package com.inventory.middle.infra.plan.persistence.condition.plan;

import lombok.Data;

import java.util.Date;

@Data
public class DemandPlanMaterialDetailReqCondition {
    private String materialCode;
    private String logicalPlantNo;
    private Integer type;
    private String tenantId;
    private Date planDate;
}
