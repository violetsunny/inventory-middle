package com.inventory.middle.interfaces.web.plan.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PlanOrderMaterialInfoVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String materialCode;
    private String materialDesc;
    private String externalMaterialCode;
    private String unit;
    private String logicalPlantNo;
    private String logicalPlantName;
    private Integer forecastInventory;
    private Integer planOrderQuantity;
    private Integer confirmQuantity;
    private Integer issueQuantity;
    private Integer finishQuantity;
}
