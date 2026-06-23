package com.inventory.middle.application.plan.plan.config.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PlanMaterialParamImportDetailBO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer index;
    private String materialCode;
    private String failedReason;
}
