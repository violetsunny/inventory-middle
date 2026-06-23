package com.inventory.middle.application.plan.config.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PlanMaterialImportDetailBO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer index;
    private String materialCode;
    private String logicalPlantNo;
    private String failedReason;
}
