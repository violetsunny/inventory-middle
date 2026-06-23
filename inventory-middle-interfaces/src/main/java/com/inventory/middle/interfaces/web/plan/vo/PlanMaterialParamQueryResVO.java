package com.inventory.middle.interfaces.web.plan.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 查询物料计划参数 返参
 * @date 2021/9/30 16:03
 */
@Data
public class PlanMaterialParamQueryResVO implements Serializable {

    private static final long serialVersionUID = 5985374808265560922L;

    @Schema(description = "物料计划参数ID")
    private Long id;

    @Schema(description = "物料编码")
    private String materialCode;

    @Schema(description = "外部物料编码")
    private String externalMaterialCode;

    @Schema(description = "逻辑仓编码")
    private String logicalPlantNo;

    @Schema(description = "逻辑仓名称")
    private String logicalPlantName;

    @Schema(description = "计划类型code（0：采购，1：调拨，2：生产）")
    private Integer planTypeCode;

    @Schema(description = "计划类型描述（0：采购，1：调拨，2：生产）")
    private String planTypeDesc;

    @Schema(description = "物料需求类型（0：独立）")
    private Integer demandType;

    @Schema(description = "需求策略编码")
    private String demandStrategyCode;

    @Schema(description = "物料提前期")
    private Integer vendorLeadTime;

    @Schema(description = "计划时区")
    private Integer planningTimeFence;

    @Schema(description = "需求时区")
    private Integer demandTimeFence;

    @Schema(description = "订货批量")
    private Long orderQuantity;

    @Schema(description = "最小订货批量")
    private Long minOrderQuantity;

    @Schema(description = "订货周期")
    private Integer orderCycleTime;

    @Schema(description = "安全库存计算类型")
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
     * 调拨源头仓
     */
    @Schema(description = "调拨源头仓")
    private String transferLogicalPlantNo;

}