package com.inventory.middle.application.plan.calculate.support.generate;

import com.inventory.middle.infra.plan.util.DateUtils;
import com.inventory.middle.domain.plan.common.bo.MaterialBO;
import com.inventory.middle.application.plan.config.bo.PlanBO;
import com.inventory.middle.application.plan.config.bo.PlanMaterialParameterBO;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 物料计划执行上下文
 *
 * @author Danny.Lee
 * @date 2021/9/26
 */
@Data
public class MaterialPlanGeneContext {

    /**
     * 产出类型
     */
    private PlanGeneType geneType;

    /**
     * 期初库存
     */
    private BigDecimal initialStock;

    /**
     * 计划配置
     */
    private PlanBO plan;

    /**
     * 物料编码
     */
    private MaterialBO material;

    /**
     * 物料计划参数
     */
    private PlanMaterialParameterBO planMaterialParameter;


    public LocalDate planStartDate() {
        return LocalDate.now().plusDays(1);
    }

    public LocalDate planEndDate() {
        return DateUtils.toLocalDate(plan.getPlanEndTime());
    }
}
