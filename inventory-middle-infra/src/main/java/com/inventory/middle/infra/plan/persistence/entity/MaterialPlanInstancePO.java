package com.inventory.middle.infra.plan.persistence.entity;

import com.inventory.middle.infra.persistence.entity.BasePO;
import lombok.Data;


import java.util.Date;

/**
 * pl_material_plan_instance
 *
 * @date 2021-09-28
 */
@Data
public class MaterialPlanInstancePO extends BasePO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 实例id
     */
    private Long instanceId;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 物料描述
     */
    private String materialDesc;

    /**
     * 物料层级
     */
    private String materialLevel;

    /**
     * 逻辑仓编码
     */
    private String logicalPlantNo;

    /**
     * 逻辑仓名称
     */
    private String logicalPlantName;

    /**
     * 需求响应策略编码
     */
    private String demandStrategyCode;

    /**
     * 物料计划状态
     */
    private Integer status;

    /**
     * 根节点id
     */
    private Long rootId;

    /**
     * 父节点id
     */
    private Long parentId;

    /**
     * 物料计划扩展属性
     */
    private String extAttrs;

    /**
     * 产出类型
     */
    private Integer geneType;

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