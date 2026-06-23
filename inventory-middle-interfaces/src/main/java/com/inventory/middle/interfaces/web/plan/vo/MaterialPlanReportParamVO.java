package com.inventory.middle.interfaces.web.plan.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 物料计划报表参数
 *
 * @author Danny.Lee
 * @date 2021/10/15
 */
@Data
@Schema(description = "物料计划报表参数")
public class MaterialPlanReportParamVO implements Serializable {

    private static final long serialVersionUID = 288005249191126384L;

    /**
     * 提前期
     */
    @Schema(description = "提前期")
    private Integer vendorLeadTime;
    /**
     * 当前库存
     */
    @Schema(description = "当前库存")
    private BigDecimal currentStock;
    /**
     * 安全库存
     */
    @Schema(description = "安全库存")
    private Long safetyStock;
    /**
     * 批量数量
     */
    @Schema(description = "批量数量")
    private Long orderQuantity;
    /**
     * 需求时区
     */
    @Schema(description = "需求时区")
    private Integer demandTimeFence;
    /**
     * 计划时区
     */
    @Schema(description = "计划时区")
    private Integer planningTimeFence;
    /**
     * 产出类型
     */
    @Schema(description = "产出类型:0-固定批量/1-净需求/2-目标库存差额")
    private Integer produceType;

    /**
     * 产出类型
     */
    @Schema(description = "产出类型:0-固定批量/1-净需求/2-目标库存差额")
    private String produceTypeDesc;
}
