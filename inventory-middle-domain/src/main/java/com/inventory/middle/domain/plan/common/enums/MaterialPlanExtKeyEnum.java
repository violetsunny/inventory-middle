package com.inventory.middle.domain.plan.common.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * 物料计划初始参数
 *
 * @author Danny.Lee
 * @date 2021/10/19
 */
@Getter
public enum MaterialPlanExtKeyEnum {

    /**
     * 产出类型
     */
    GENE_TYPE("geneType", "产出类型"),

    /**
     * 计划开始日期
     */
    START_DATE("planStartDate", "计划开始日期"),

    /**
     * 期初库存
     */
    INITIAL_STOCK("initialStock", "期初库存"),

    /**
     * 期初-预计可用库存
     */
    INITIAL_PAB("initialPab", "期初预计可用库存"),

    /**
     * 期初-计划投入量
     */
    INITIAL_PLAN_INVEST("initialPlanInvest", "期初计划投入量"),

    /**
     * 产出方式
     */
    PRODUCE_TYPE("productType", "产出方式"),

    /**
     * 需求响应策略
     */
    DEMAND_STRATEGY("demandStrategyCode", "需求响应策略"),

    /**
     * 物料计划类型
     */
    MATERIAL_PLAN_TYPE("materialPlanType", "物料计划类型"),

    /**
     * 物料提前期
     */
    VENDOR_LEAD_TIME("vendorLeadTime", "物料提前期"),

    /**
     * 需求时区
     */
    DEMAND_TIME_FENCE("demandTimeFence", "需求时区"),

    /**
     * 计划时区
     */
    PLANNING_TIME_FENCE("planningTimeFence", "计划时区"),

    /**
     * 批次数量
     */
    ORDER_QUANTITY("orderQuantity", "批次数量"),

    /**
     * 订货周期
     */
    ORDER_CYCLE_TIME("orderCycleTime", "订货周期"),

    /**
     * 安全库存
     */
    SAFETY_STOCK("safetyStock", "安全库存"),

    /**
     * 损耗率百分比
     */
    LOSS_RATE("lossRate", "损耗率百分比"),

    /**
     * 成品率百分比
     */
    FINISHED_RATE("finishedRate", "成品率百分比"),

    /**
     * 启用损耗率
     */
    ENABLE_LOSS_RATE("enableLossRate", "启用损耗率"),

    /**
     * 启用成品率
     */
    ENABLE_FINISHED_RATE("enableFinishedRate", "启用成品率"),

    /**
     * 启用安全库存
     */
    ENABLE_SAFETY_STOCK("enableSafetyStock", "启用安全库存"),

    /**
     * 启用Bom
     */
    ENABLE_BOM("enableBom", "启用Bom"),

    /**
     * Bom树结构
     */
    BOM_TREE("bomTree", "Bom树结构"),
    ;

    private final String code;

    private final String desc;

    MaterialPlanExtKeyEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static MaterialPlanExtKeyEnum of(String code) {
        return Arrays.stream(MaterialPlanExtKeyEnum.values())
                .filter(item -> Objects.equals(item.getCode(), code))
                .findFirst()
                .orElse(null);
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
