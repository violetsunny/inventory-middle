package com.inventory.middle.application.plan.plan.calculate.support.formula.factor;

import com.inventory.middle.application.plan.plan.config.enums.OrderProduceTypeEnum;
import com.inventory.middle.domain.plan.common.enums.PlanMaterialParamPlanTypeEnum;
import com.inventory.middle.domain.plan.common.enums.PlanProduceEnum;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 物料计划参数因子
 *
 * @author Danny.Lee
 * @date 2021/9/27
 */
@Data
public class ParameterFactor implements Factor{

    /**
     * 计划类型
     */
    private PlanMaterialParamPlanTypeEnum planType;

    /**
     * 产出类型
     */
    @Deprecated
    private PlanProduceEnum produceType;

    private OrderProduceTypeEnum produceType2;

    /**
     * 需求响应策略
     */
    private String demandStrategyCode;

    /**
     * 物料提前期
     */
    private Integer vendorLeadTime;

    /**
     * 需求时区
     */
    private Integer demandTimeFence;

    /**
     * 计划时区
     */
    private Integer planningTimeFence;

    /**
     * 批次数量
     */
    private Long orderQuantity;

    /**
     * 补货周期
     */
    private Integer orderCycleTime;

    /**
     * 安全库存
     */
    private BigDecimal safetyStock;

    /**
     * 损耗率百分比
     */
    private BigDecimal lossRate;

    /**
     * 成品率百分比
     */
    private BigDecimal finishedRate;

    /**
     * 启用损耗率
     */
    private Boolean enableLossRate;

    /**
     * 启用成品率
     */
    private Boolean enableFinishedRate;

    /**
     * 启用安全库存
     */
    private Boolean enableSafetyStock;
}
