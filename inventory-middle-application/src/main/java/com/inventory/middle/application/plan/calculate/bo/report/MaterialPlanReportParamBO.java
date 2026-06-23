package com.inventory.middle.application.plan.calculate.bo.report;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.inventory.middle.domain.plan.common.bo.BaseBo;

import java.math.BigDecimal;

/**
 * 物料计划报表参数
 *
 * @author Danny.Lee
 * @date 2021/10/2
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MaterialPlanReportParamBO extends BaseBo {

    private static final long serialVersionUID = 4463625543064042431L;

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
     * 补货规则
     */
    private Integer produceType;

    @Override
    public String toLog() {
        return this.toString();
    }
}
