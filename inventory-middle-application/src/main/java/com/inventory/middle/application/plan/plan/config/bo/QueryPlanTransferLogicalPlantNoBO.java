package com.inventory.middle.application.plan.plan.config.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class QueryPlanTransferLogicalPlantNoBO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String materialCode;
    private String logicalPlantNo;
    private String tenantId;
}
