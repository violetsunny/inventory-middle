package com.inventory.middle.infra.plan.persistence.entity;

import com.inventory.middle.infra.persistence.entity.BasePO;
import lombok.Data;


import java.util.Date;

/**
 * pl_plan_instance
 *
 * @date 2021-09-28
 */
@Data
public class PlanInstancePO extends BasePO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 方案id
     */
    private Long planId;

    /**
     * 方案编码
     */
    private String planCode;

    /**
     * 计划版本号
     */
    private String planVersion;

    /**
     * 方案类型
     */
    private Integer planType;

    /**
     * 计划执行结果（0-未开始/1-执行中/2-已失败/3-已完结）
     */
    private Integer status;

    /**
     * 计划执行开始时间
     */
    private Date calStartTime;

    /**
     * 计划执行结束时间
     */
    private Date calEndTime;

    /**
     * 是否删除（0-未删除/1-已删除，默认0）
     *//**
     * 创建时间（默认当前时间）
     *//**
     * 创建人（0-系统）
     *//**
     * 租户id
     */
    private String tenantId;
}