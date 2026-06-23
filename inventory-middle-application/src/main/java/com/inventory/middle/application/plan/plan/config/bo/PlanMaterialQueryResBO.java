package com.inventory.middle.application.plan.plan.config.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PlanMaterialQueryResBO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String materialCode;
    private String logicalPlantNo;
    private String materialDesc;
}
