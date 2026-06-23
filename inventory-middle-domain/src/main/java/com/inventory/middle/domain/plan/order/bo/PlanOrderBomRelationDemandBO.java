package com.inventory.middle.domain.plan.order.bo;

import lombok.Data;

/**
 * 计划订单 BOM关联需求BO
 * <p>
 * 迁移自 com.enn.plan.management.core.plan.order.bo.PlanOrderBomRelationDemandBO
 * </p>
 *
 * @author migrated from scm-plan-management
 */
@Data
public class PlanOrderBomRelationDemandBO {

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 逻辑仓编码
     */
    private String logicalPlantNo;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 需求量
     */
    private Integer amount;

}
