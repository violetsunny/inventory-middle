package com.inventory.middle.application.plan.config.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class PlanUpdateReqBO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String planDesc;
    private List<String> coverLogicalPlantNos;
    private Integer coverMaterialType;
    private String planStartTimeStr;
    private Integer planType;
    private Integer planHorizon;
    private List<String> demandPlanIds;
    private Map<String, Object> planCalculateParams;
    private Map<String, Object> planDeliveryParams;
    private Integer relatedBom;
}
