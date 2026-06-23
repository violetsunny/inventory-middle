package com.inventory.middle.infra.plan.persistence.condition.demand;

import lombok.Data;

import java.util.Date;

@Data
public class DemandPlanSelectReqCondition {

    private String planVersion;

    private String companyName;

    private String logicalPlantNo;

    private String warehouseNo;

    private Integer aggregationPeriod;

    private Integer status;

    private String createUserName;

    private Date beginTime;

    private Date endTime;

    private String tenantId;

    private Integer demandType;

    private Integer demandSourceType;
}
