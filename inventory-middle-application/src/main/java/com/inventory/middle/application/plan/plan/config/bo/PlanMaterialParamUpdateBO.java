package com.inventory.middle.application.plan.plan.config.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class PlanMaterialParamUpdateBO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Integer planType;
    private Integer demandType;
    private String demandStrategyCode;
    private Integer vendorLeadTime;
    private Integer demandTimeFence;
    private Integer planningTimeFence;
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
