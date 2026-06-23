package com.inventory.middle.infra.plan.persistence.condition.plan;

import lombok.Data;

import java.io.Serializable;

@Data
public class PlanQueryByLogicalPlantNoCondition implements Serializable {

    private static final long serialVersionUID = -1617592276760531343L;

    private String tenantId;

    private String logicalPlantNo;
}
