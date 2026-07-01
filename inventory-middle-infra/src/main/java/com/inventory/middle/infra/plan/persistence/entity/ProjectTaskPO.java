package com.inventory.middle.infra.plan.persistence.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * pl_project_task
 *
 * @date 2026-07-01
 */
@Data
public class ProjectTaskPO implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 流水号
     */
    private String requestId;

    /**
     * 任务编号
     */
    private String taskNo;

    /**
     * 方案ID
     */
    private Long projectId;

    /**
     * 任务类型
     */
    private String projectType;

    /**
     * 任务规则参数（JSON）
     */
    private String taskRule;

    /**
     * 任务数据参数（JSON）
     */
    private String taskData;

    /**
     * 请求状态（0-待处理/1-处理中/2-已完成/3-失败）
     */
    private Integer requestStatus;

    /**
     * 请求报文
     */
    private String requestBody;

    /**
     * 原始返回报文
     */
    private String originalBody;

    /**
     * 优化结果（JSON）
     */
    private String optimizeResult;

    /**
     * 预计库存（JSON数组）
     */
    private String predictInventory;

    /**
     * 船期计划出货量（JSON数组）
     */
    private String shipPlanCheckAmount;

    /**
     * 船期计划时间（JSON数组）
     */
    private String shipPlanTime;

    /**
     * 船期计划ID（JSON数组）
     */
    private String shipPlanId;

    /**
     * 可用库存下限（JSON数组）
     */
    private String availableInventoryDown;

    /**
     * 返回码
     */
    private Long calResultCode;

    /**
     * 优化指标结果
     */
    private String optTarget;

    /**
     * 临时数据
     */
    private String tempData;

    /**
     * 算法计算请求ID
     */
    private String reRequestId;

    /**
     * 算法计算任务编号
     */
    private String reTaskNo;

    /**
     * 算法计算创建时间
     */
    private LocalDateTime reCreateTime;

    /**
     * 是否删除（0:未删除/1:已删除）
     */
    private Integer isDelete;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    private String createUserId;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 更新人
     */
    private String updateUserId;

    /**
     * 租户id
     */
    private String tenantId;

}
