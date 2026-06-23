package com.inventory.middle.infra.plan.persistence.entity;

import com.inventory.middle.infra.persistence.entity.BasePO;
import lombok.Data;


import java.util.Date;

/**
 * pl_plan_material_parameter
 *
 * @date 2021-11-10
 */
@Data
public class PlanMaterialParameterPO extends BasePO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 逻辑仓编码
     */
    private String logicalPlantNo;

    /**
     * 逻辑仓名称
     */
    private String logicalPlantName;

    /**
     * 计划类型（0：采购，1：调拨，2：生产）
     */
    private Integer planType;

    /**
     * 物料需求类型
     */
    private Integer demandType;

    /**
     * 需求策略编码
     */
    private String demandStrategyCode;

    /**
     * 物料提前期
     */
    private Integer vendorLeadTime;

    /**
     * 计划时界
     */
    private Integer planningTimeFence;

    /**
     * 需求时界
     */
    private Integer demandTimeFence;

    /**
     * 订货批量
     */
    private Long orderQuantity;

    /**
     * 最小订货批量
     */
    private Long minOrderQuantity;

    /**
     * 订货周期
     */
    private Integer orderCycleTime;

    /**
     * 安全库存计算方式
     */
    private Integer safetyStockCalType;

    /**
     * 安全库存
     */
    private Long safetyStock;

    /**
     * 安全库存公式计算参数
     */
    private String safetyStockFactors;

    /**
     * 损耗率
     */
    private Integer lossRate;

    /**
     * 成品率
     */
    private Integer finishedRate;

    /**
     * 是否已删除（0:未删除，1:已删除，默认为0）
     *//**
     * 创建时间（默认当前时间）
     *//**
     * 创建人
     *//**
     * 更新时间（默认当前时间）
     *//**
     * 更新人
     *//**
     * 操作人姓名
     *//**
     * 租户Id
     */
    private String tenantId;

    /**
     * 调拨逻辑仓
     */
    private String transferLogicalPlantNo;

    /**
     * 物料描述
     */
    private String materialDesc;

    private String operatorName;
}