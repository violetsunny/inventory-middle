package com.inventory.middle.infra.plan.persistence.entity;

import com.inventory.middle.infra.persistence.entity.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 计划订单下发详情 PO。
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PlanOrderIssueDetailPO extends BasePO {

    private Long id;
    private String issueNo;
    private Long planOrderId;
    private String materialCode;
    private String logicalPlantNo;
    private Integer issueQuantity;
    private Integer finishQuantity;
    private String currentStatus;
    private String tenantId;
    private String operatorName;
}
