package com.inventory.middle.infra.plan.persistence.condition.demand;

import lombok.Data;

import java.util.List;

@Data
public class DemandBoardMaterialCondition {

    private String logicalPlantNo;

    private String tenantId;

    private List<String> materialCodeList;

    private long start;

    private int pageSize;
}
