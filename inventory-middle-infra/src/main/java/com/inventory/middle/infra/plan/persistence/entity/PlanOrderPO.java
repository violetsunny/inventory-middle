package com.inventory.middle.infra.plan.persistence.entity;

import com.inventory.middle.infra.persistence.entity.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 计划订单 PO。
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PlanOrderPO extends BasePO {

    private Long id;
    private String orderNo;
    private String planCode;
    private String materialCode;
    private String materialDesc;
    private String externalMaterialCode;
    private String logicalPlantNo;
    private String logicalPlantName;
    private Integer planType;
    private Integer createType;
    private String transferLogicalPlantNo;
    private Integer forecastInventory;
    private Integer planOrderQuantity;
    private String unit;
    private Integer issueQuantity;
    private Integer confirmQuantity;
    private Integer finishQuantity;
    private Integer status;
    private Date planIssueTime;
    private Date realIssueTime;
    private Date planReceivingTime;
    private Date realReceivingTime;
    private Date confirmTime;
    private String plannerName;
    private String plannerId;
    private String demandInfo;
    private String tenantId;
    private String operatorName;
    private String extAttrs;
}
