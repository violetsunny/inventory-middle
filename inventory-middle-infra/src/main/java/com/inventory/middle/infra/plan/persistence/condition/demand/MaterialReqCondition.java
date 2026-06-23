package com.inventory.middle.infra.plan.persistence.condition.demand;

import lombok.Data;

@Data
public class MaterialReqCondition {

    private String materialDesc;

    private String tenantId;

    private String logicalPlantNo;
}
