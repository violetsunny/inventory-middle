package com.inventory.middle.application.plan.demand.handler;

import lombok.Data;

import java.util.List;

/**
 * 反算请求上下文
 * <p>
 * 迁移自 com.enn.plan.management.core.demand.handler.ReverseCalculateRequestContext
 * </p>
 *
 * @author migrated from scm-plan-management
 */
@Data
public class ReverseCalculateRequestContext {

    /**
     * 重新计算列表
     */
    private List<ReverseCalculateMaterial> materialList;

}
