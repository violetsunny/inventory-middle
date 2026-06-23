package com.inventory.middle.interfaces.web.plan.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ManualPlanOrderCreateReqDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String materialCode;
    private String materialDesc;
    private String logicalPlantNo;
    private String logicalPlantName;
    private Integer planOrderQuantity;
    private String unit;
    private Integer forecastInventory;
    private String planIssueTime;
    private String planReceivingTime;
}
