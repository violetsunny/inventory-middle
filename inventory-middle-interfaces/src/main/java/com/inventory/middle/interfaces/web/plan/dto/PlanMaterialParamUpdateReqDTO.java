package com.inventory.middle.interfaces.web.plan.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;


/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 更新物料科技化参数入参
 * @date 2021/9/26 14:10
 */
@Data
public class PlanMaterialParamUpdateReqDTO implements Serializable {

    private static final long serialVersionUID = -5735673256639940294L;

    @Schema(description = "物料计划参数ID", required = true)
    private Long id;

    @Schema(description = "计划类型 0：采购，1：调拨，2：生产", required = true)
    private Integer planType;

    @Schema(description = "物料需求类型", required = true)
    private Integer demandType;

    @Schema(description = "需求响应策略编码", required = true)
    private String demandStrategyCode;

    @Schema(description = "提前期")
    private Integer vendorLeadTime;

    @Schema(description = "需求时区")
    private Integer demandTimeFence;

    @Schema(description = "计划时区")
    private Integer planningTimeFence;

    @Schema(description = "订货批量")
    private Long orderQuantity;

    @Schema(description = "最小订货批量")
    private Long minOrderQuantity;

    @Schema(description = "订货周期")
    private Integer orderCycleTime;

    @Schema(description = "安全库存计算方式")
    private Integer safetyStockCalType;

    @Schema(description = "安全库存")
    private Long safetyStock;

    @Schema(description = "安全库存公式计算参数#安全系数")
    private Integer safetyCoefficient;

    @Schema(description = "安全库存公式计算参数#保障间隔")
    private Integer guaranteeInterval;

    @Schema(description = "安全库存公式计算参数#保障间隔内的需求标准差")
    private Integer demandStd;

    @Schema(description = "损耗率")
    private Integer lossRate;

    @Schema(description = "成品率")
    private Integer finishedRate;

    /**
     * 调拨逻辑仓编码
     */
    @Schema(description = "调拨源头仓")
    private String transferLogicalPlantNo;


}
