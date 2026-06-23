package com.inventory.middle.infra.plan.persistence.condition.plan;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class DemandSourceForPlanOrderCondition {
    private List<String> materialCodes;
    private String logicalPlantNo;
    private String tenantId;
    private List<Integer> sourceTypes;
    private Date planDateStart;
    private Date planDateEnd;
}
