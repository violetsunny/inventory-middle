package com.inventory.middle.application.plan.demand.handler;

import lombok.Data;

/**
 * 反算物料信息
 * <p>
 * 迁移自 com.enn.plan.management.core.demand.handler.ReverseCalculateMaterial
 * </p>
 *
 * @author migrated from scm-plan-management
 */
@Data
public class ReverseCalculateMaterial {

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 逻辑仓编码
     */
    private String logicalPlantNo;

    /**
     * 租户id
     */
    private String tenantId;

}
