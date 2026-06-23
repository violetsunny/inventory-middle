package com.inventory.middle.infra.plan.persistence.condition;

import lombok.Data;

import java.io.Serializable;

@Data
public class PlanMaterialQueryByPlanCondition implements Serializable {

    private static final long serialVersionUID = 6526734650522808401L;

    private String tenantId;

    private String materialCode;

    private String logicalPlantNo;

    private long planId;
}
