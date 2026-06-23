package com.inventory.middle.application.plan.config.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PlanMaterialParamImportExcelBO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String materialCode;
    private String logicalPlantNo;
    private Integer planTypeCode;
    private Integer demandType;
    private String demandStrategyCode;
    private Integer vendorLeadTime;
    private Integer planningTimeFence;
    private Integer demandTimeFence;
    private Long orderQuantity;
    private Long minOrderQuantity;
    private Integer orderCycleTime;
    private Integer safetyStockCalType;
    private Long safetyStock;
    private Integer lossRate;
    private Integer finishedRate;
    private String transferLogicalPlantNo;
}
