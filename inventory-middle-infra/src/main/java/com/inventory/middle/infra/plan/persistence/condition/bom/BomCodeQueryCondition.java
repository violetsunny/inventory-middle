package com.inventory.middle.infra.plan.persistence.condition.bom;

import lombok.Data;

import java.util.List;

@Data
public class BomCodeQueryCondition {

    private String logicalPlantNo;

    private String tenantId;

    private List<String> materialCodeList;
}
