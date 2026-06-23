package com.inventory.middle.application.plan.plan.calculate.support.generate.processor.impl;

import com.alibaba.fastjson.JSONObject;
import com.inventory.middle.application.plan.plan.calculate.bo.MaterialPlanDetailBO;
import com.inventory.middle.application.plan.plan.calculate.bo.MaterialPlanInstanceBO;
import com.inventory.middle.application.plan.plan.calculate.bo.PlanInstanceBO;
import com.inventory.middle.application.plan.plan.calculate.support.generate.processor.MaterialPlanInstancePostProcessor;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static com.inventory.middle.domain.plan.common.enums.MaterialPlanDetailBusinessExtKeyEnum.TRANSFER_VENDOR_LEAD_TIME;
import static com.inventory.middle.domain.plan.common.enums.MaterialPlanExtKeyEnum.INITIAL_PLAN_INVEST;
import static com.inventory.middle.domain.plan.common.enums.MaterialPlanExtKeyEnum.MATERIAL_PLAN_TYPE;
import static com.inventory.middle.domain.plan.common.enums.PlanMaterialParamPlanTypeEnum.TRANSFER;
import static com.inventory.middle.application.plan.plan.calculate.support.formula.indicator.Indicators.PLAN_INVEST;

/**
 * 计划投入量重排后置处理器
 * <p>
 * Created on 2021/11/24.
 *
 * @author Danny.Lee
 */
@Slf4j
@Order(1)
@Component
public class MpiPlanInvestRearrangePostProcessor implements MaterialPlanInstancePostProcessor {

    @Override
    public void postProcess(PlanInstanceBO planInstance, MaterialPlanInstanceBO materialPlanInstance) {
        if (!whetherPlanNeedRearrange(materialPlanInstance)) {
            return;
        }

        try {
            Map<LocalDate, BigDecimal> rearrangeIndicators = Maps.newHashMap();
            for (Map.Entry<LocalDate,MaterialPlanDetailBO> entry : materialPlanInstance.getMaterialPlanDetails().entrySet()) {
                final LocalDate date = entry.getKey();
                final MaterialPlanDetailBO detail = entry.getValue();
                final BigDecimal planInvest = detail.getIndicators().get(PLAN_INVEST.getIndicatorCode());

                // 重排提前天数
                int rearrangeDay = this.rearrangeForwardDays(detail);
                // 计划投入量-重排日期
                LocalDate dateToRearrange = date.minusDays(rearrangeDay);
                if (LocalDate.now().isAfter(dateToRearrange)) {
                    dateToRearrange = LocalDate.now();
                }
                // 指标汇总
                accumulate(rearrangeIndicators, dateToRearrange, planInvest);

                if (rearrangeDay > 0) {
                    // 计划投入发生重排时，将重排日期放入指标扩展信息中
                    final String rearrangeDate = dateToRearrange.format(DateTimeFormatter.ISO_LOCAL_DATE);
                    Optional.ofNullable(detail.getIndicatorExtAttrs().get(PLAN_INVEST.getIndicatorCode()))
                            .ifPresent(extAttrs -> extAttrs.put("rearrangeDate", rearrangeDate));
                }
            }

            // 计划投入量重排
            for (Map.Entry<LocalDate,MaterialPlanDetailBO> entry : materialPlanInstance.getMaterialPlanDetails().entrySet()) {
                final LocalDate date = entry.getKey();
                final MaterialPlanDetailBO detail = entry.getValue();
                // 由于计划投入的重排会导致指标前移，计划期内物料计划详情可能由于迁移导致不存在重排指标的情况，此时计划投入量置0
                BigDecimal planInvestAmount = Optional.ofNullable(rearrangeIndicators.remove(date)).orElse(BigDecimal.ZERO);
                detail.getIndicators().put(PLAN_INVEST.getIndicatorCode(), planInvestAmount);
            }

            // 期初计划投入量
            BigDecimal initPlanInvest = Optional.ofNullable(materialPlanInstance.getExtAttrs().getBigDecimal(INITIAL_PLAN_INVEST.getCode()))
                    .orElse(BigDecimal.ZERO);
            initPlanInvest = initPlanInvest.add(rearrangeIndicators.getOrDefault(LocalDate.now(), BigDecimal.ZERO));
            materialPlanInstance.getExtAttrs().put(INITIAL_PLAN_INVEST.getCode(), initPlanInvest);


        } catch (Exception e) {
            log.error("processor={} process error", getClass().getSimpleName(), e);
        }
    }

    /**
     * 物料计划是否需要计划投入重排<br/>
     * 物料计划参数为调拨场景时，需要考虑计划投入量指标重排
     *
     * @param materialPlanInstance 物料计划实例
     * @return true标识该物料计划需要进行计划投入量重排
     */
    private boolean whetherPlanNeedRearrange(MaterialPlanInstanceBO materialPlanInstance) {
        if (MapUtils.isEmpty(materialPlanInstance.getMaterialPlanDetails())) {
            return false;
        }
        JSONObject extAttrs = materialPlanInstance.getExtAttrs();
        return null != extAttrs &&
                Objects.equals(TRANSFER.getCode(), extAttrs.getInteger(MATERIAL_PLAN_TYPE.getCode()));
    }

    /**
     * 计划投入量指标汇总
     *
     * @param rearrangeIndicators 重排指标，用于数据汇总
     * @param dateToRearrange     重排日期
     * @param planInvestAmount    汇总计划投入量指标
     */
    private void accumulate(Map<LocalDate, BigDecimal> rearrangeIndicators, LocalDate dateToRearrange, BigDecimal planInvestAmount) {
        BigDecimal rearrangePlanInvestAmount = rearrangeIndicators.getOrDefault(dateToRearrange, BigDecimal.ZERO);
        rearrangeIndicators.put(dateToRearrange, rearrangePlanInvestAmount.add(planInvestAmount));
    }

    /**
     * 计算重排提前天数
     *
     * @param detail 物料计划详情
     * @return 重排提前天数
     */
    private int rearrangeForwardDays(MaterialPlanDetailBO detail) {
        String transferVendorLeadTime = detail.getBusinessExt(TRANSFER_VENDOR_LEAD_TIME.getCode());
        return Optional.ofNullable(transferVendorLeadTime)
                .map(Integer::parseInt)
                .orElse(0);
    }
}
