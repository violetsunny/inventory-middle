package com.inventory.middle.domain.plan.common.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 计划域 BO 基类
 * <p>
 * 迁移自: com.enn.plan.management.core.common.bo.BaseBO
 * </p>
 */
@Data
public class PlanBaseBO implements Serializable {

    private static final long serialVersionUID = -45091434594589996L;

    /**
     * token（已废弃，保留向后兼容）
     */
    @Deprecated
    private String token;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 租户id
     */
    private String tenantId;
}
