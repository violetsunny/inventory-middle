package com.inventory.middle.infra.plan.persistence.entity;

import com.inventory.middle.infra.persistence.entity.BasePO;
import lombok.Data;


import java.util.Date;

/**
 * pl_material_plan_detail
 *
 * @date 2021-09-28
 */
@Data
public class MaterialPlanDetailPO extends BasePO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 计划实例详情id
     */
    private Long materialInstanceId;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 物料描述
     */
    private String materialDesc;

    /**
     * 逻辑仓编码
     */
    private String logicalPlantNo;

    /**
     * 计划日期
     */
    private Date planDate;

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

    /**
     * 计划详情（JSON）
     */
    private String planDetail;
}