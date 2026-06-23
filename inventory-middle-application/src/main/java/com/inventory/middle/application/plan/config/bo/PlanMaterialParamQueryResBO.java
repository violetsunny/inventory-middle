package com.inventory.middle.application.plan.config.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class PlanMaterialParamQueryResBO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String materialCode;
    private String externalMaterialCode;
    private String logicalPlantNo;
    private String logicalPlantName;
    private Integer planTypeCode;
    private String planTypeDesc;
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
    private Map<String, Object> safetyStockFactors;
    private Integer lossRate;
    private Integer finishedRate;
    private String transferLogicalPlantNo;
}
