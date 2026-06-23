package com.inventory.middle.infra.plan.persistence.entity;

import lombok.Data;

/**
 * 变更计划状态 PO（轻量更新对象）
 */
@Data
public class ChangeStatusPlanPO {

    /**
     * 计划方案主键
     */
    private Long id;

    /**
     * 计划状态
     */
    private Integer status;
}
