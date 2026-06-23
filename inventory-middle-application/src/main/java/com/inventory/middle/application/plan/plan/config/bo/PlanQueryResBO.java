package com.inventory.middle.application.plan.plan.config.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@Data
public class PlanQueryResBO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String planCode;
    private Integer coverMaterialType;
    private String planDesc;
    private Integer planType;
    private Integer planHorizon;
    private Date planStartTime;
    private String planStartTimeStr;
    private Integer status;
    private String operatorId;
    private Date updateTime;
    private String updateTimeStr;
    private String tenantId;
    private Map<Long, String> demandPlanFile;
    private Map<String, String> coverLogicalPlant;
    private Map<String, Object> planDeliveryParams;
    private Map<String, Object> planCalculateParams;
    private Integer relatedBom;
}
