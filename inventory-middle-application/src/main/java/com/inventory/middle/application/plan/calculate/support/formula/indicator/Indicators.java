package com.inventory.middle.application.plan.calculate.support.formula.indicator;

import lombok.Getter;

/**
 * 指标枚举
 *
 * @author Danny.Lee
 * @date 2021/10/13
 */
@Getter
public enum Indicators {
    /**
     * 可供销售量
     */
    ATP("atp", "可供销售量"),
    /**
     * 毛需求
     */
    GROSS_REQUIREMENT("grossRequirement", "毛需求/本期计划出"),
    /**
     * 净需求
     */
    NET_REQUIREMENT("netRequirement", "净需求/预测库存"),
    /**
     * 订单量
     */
    ORDER("orderRequirement", "合同量/订单量"),
    /**
     * 计划投入量
     */
    PLAN_INVEST("planInvest", "计划投入量"),
    /**
     * 计划产出量
     */
    PLAN_PRODUCE("planProduce", "计划产出量"),
    /**
     * 计划接收量
     */
    PLAN_RECEIVE("planReceive", "计划接收量"),
    /**
     * 预测量
     */
    PREDICT("predictRequirement", "预测量"),
    /**
     * 可用库存
     */
    AVAILABLE_INVENTORY("availableInventory", "可用库存量"),
    /**
     * 预计可用库存
     */
    PAB("pab", "预计可用库存量"),
    /**
     * 补货点
     */
    REPLENISHMENT_POINT("replenishmentPoint", "补货点"),
    /**
     * 目标库存
     */
    TARGET_INVENTORY("targetInventory", "目标库存"),
    /**
     * 相关需求
     */
    CORRELATED("correlated", "相关需求"),
    /**
     * MPS计划投入量
     */
    MPS_PLAN_INVEST("mpsPlanInvest", "MPS计划投入量"),
    /**
     * MPS计划产出量
     */
    MPS_PLAN_PRODUCE("mpsPlanProduce", "MPS计划产出量"),
    ;

    private final String indicatorCode;

    private final String indicatorName;

    Indicators(String indicatorCode, String indicatorName) {
        this.indicatorCode = indicatorCode;
        this.indicatorName = indicatorName;
    }

    public static Indicators[] ofDefaultReport() {
        return new Indicators[]{
                PREDICT,
                ORDER,
                GROSS_REQUIREMENT,
                PLAN_RECEIVE,
                PAB,
                NET_REQUIREMENT,
                PLAN_PRODUCE,
                PLAN_INVEST,
                ATP,
                REPLENISHMENT_POINT,
                TARGET_INVENTORY
        };
    }

    public static Indicators[] ofCorrelatedReport() {
        return new Indicators[]{
                MPS_PLAN_PRODUCE,
                MPS_PLAN_INVEST,
                CORRELATED,
                GROSS_REQUIREMENT,
                PLAN_RECEIVE,
                PAB,
                NET_REQUIREMENT,
                PLAN_PRODUCE,
                PLAN_INVEST,
                REPLENISHMENT_POINT,
                TARGET_INVENTORY
        };
    }
}
