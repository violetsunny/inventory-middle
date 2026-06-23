package com.inventory.middle.application.plan.plan.config.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PlanMaterialLogicalPlantBO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String logicalPlantNo;
    private String logicalPlantName;
}
