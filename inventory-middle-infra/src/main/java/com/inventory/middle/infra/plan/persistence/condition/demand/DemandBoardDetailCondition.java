package com.inventory.middle.infra.plan.persistence.condition.demand;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class DemandBoardDetailCondition {

    private String logicalPlantNo;

    private List<String> materialCodeList;

    private String tenantId;

    private Date beginTime;

    private Date endTime;
}
