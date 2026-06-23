package com.inventory.middle.application.plan.config.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PlanMaterialParamPageResBO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String materialCode;
    private String externalMaterialCode;
    private String logicalPlantName;
    private String logicalPlantNo;
    private String createTime;
    private String operatorName;
    private String materialDesc;
    private String transferLogicalPlantNo;
}
