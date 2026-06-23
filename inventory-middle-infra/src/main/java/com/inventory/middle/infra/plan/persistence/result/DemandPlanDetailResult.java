package com.inventory.middle.infra.plan.persistence.result;

import lombok.Data;

import java.util.List;

@Data
public class DemandPlanDetailResult {

    private Long planMaterialId;

    private String materialCode;

    private String materialDesc;

    private String materialUnit;

    private String logicalPlantNo;

    private List<PeriodDemandResult> demandList;
}
