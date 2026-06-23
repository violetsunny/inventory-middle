package com.inventory.middle.client.plan.config.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 物料计划报表参数
 *
 * @author Danny.Lee
 * @date 2021/10/2
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MaterialPlanReportParamDTO implements Serializable {

    private static final long serialVersionUID = -7327037107205340115L;
    /**
     * 物料提前期
     */
    private Integer vendorLeadTime;

    /**
     * 当前/期初库存
     */
    private BigDecimal currentStock;

    /**
     * 安全库存
     */
    private Long safetyStock;

    /**
     * 订货批量
     */
    private Long orderQuantity;

    /**
     * 需求时区
     */
    private Integer demandTimeFence;

    /**
     * 计划时区
     */
    private Integer planningTimeFence;

    /**
     * 产出类型<br/>
     * 0-定量-批量
     * 1-不定量-净需求
     * 2-不定量-目标库存差额
     */
    private Integer produceType;

    /**
     * 产出类型描述
     */
    private String produceTypeDesc;
}
