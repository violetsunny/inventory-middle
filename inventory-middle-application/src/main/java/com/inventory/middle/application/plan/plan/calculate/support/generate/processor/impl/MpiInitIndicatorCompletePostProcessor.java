package com.inventory.middle.application.plan.plan.calculate.support.generate.processor.impl;

import com.alibaba.fastjson.JSONObject;
import com.inventory.middle.application.plan.plan.calculate.bo.MaterialPlanInstanceBO;
import com.inventory.middle.application.plan.plan.calculate.bo.PlanInstanceBO;
import com.inventory.middle.application.plan.plan.calculate.support.formula.FormulaContext;
import com.inventory.middle.application.plan.plan.calculate.support.formula.Formulas;
import com.inventory.middle.application.plan.plan.calculate.support.formula.factor.MaterialFactor;
import com.inventory.middle.application.plan.plan.calculate.support.formula.factor.ParameterFactor;
import com.inventory.middle.application.plan.plan.calculate.support.formula.indicator.Indicator;
import com.inventory.middle.application.plan.plan.calculate.support.formula.indicator.Indicators;
import com.inventory.middle.application.plan.plan.calculate.support.generate.processor.MaterialPlanInstancePostProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import static com.inventory.middle.domain.plan.common.enums.MaterialPlanExtKeyEnum.*;

/**
 * 物料计划报表期初指标补全处理
 * <p>
 * Created on 2021/11/22.
 *
 * @author Danny.Lee
 */
@Slf4j
@Order(0)
@Component
public class MpiInitIndicatorCompletePostProcessor implements MaterialPlanInstancePostProcessor {

    @Override
    public void postProcess(PlanInstanceBO planInstance, MaterialPlanInstanceBO materialPlanInstance) {


        FormulaContext context = FormulaContext.get();

        try {
            // PAB从公式上下文中获取
            MaterialFactor factor = this.buildFactor(materialPlanInstance);
            Indicator pabIndicator = context.getIndicator(factor, LocalDate.now(), Indicators.PAB.getIndicatorCode());
            if (null != pabIndicator) {
                materialPlanInstance.getExtAttrs().put(INITIAL_PAB.getCode(), pabIndicator.value().setScale(0, RoundingMode.UNNECESSARY));
            }

            // PLAN_INVEST公式计算获取
            factor.setPlanDate(LocalDate.now());
            BigDecimal initPlanInvest = Formulas.formula(Indicators.PLAN_INVEST).apply(factor).value();
            materialPlanInstance.getExtAttrs().put(INITIAL_PLAN_INVEST.getCode(), initPlanInvest);

        } catch (Exception ex) {
            log.error("processor={} process error", getClass().getSimpleName(), ex);
        }
    }

    private MaterialFactor buildFactor(MaterialPlanInstanceBO materialPlanInstance) {
        // 待优化REFINE
        final JSONObject extAttrs = materialPlanInstance.getExtAttrs();
        MaterialFactor factor = new MaterialFactor();
        factor.setTenantId(materialPlanInstance.getTenantId());
        factor.setMaterialCode(materialPlanInstance.getMaterialCode());
        factor.setLogicalPlantNo(materialPlanInstance.getLogicalPlantNo());
        factor.setPlanStartDate(LocalDate.now().plusDays(1));
        factor.setCurrentStock(extAttrs.getBigDecimal(INITIAL_STOCK.getCode()));

        ParameterFactor parameterFactor = new ParameterFactor();
        parameterFactor.setVendorLeadTime(extAttrs.getInteger(VENDOR_LEAD_TIME.getCode()));

        factor.setParameterFactor(parameterFactor);
        return factor;
    }


}
