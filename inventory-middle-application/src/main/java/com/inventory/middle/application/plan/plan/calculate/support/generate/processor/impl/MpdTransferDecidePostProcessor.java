package com.inventory.middle.application.plan.plan.calculate.support.generate.processor.impl;

import com.inventory.middle.domain.plan.common.enums.PlanStatusEnum;
import com.inventory.middle.infra.plan.util.DateUtils;
import com.inventory.middle.domain.plan.common.bo.MaterialBO;
import com.inventory.middle.application.plan.plan.calculate.bo.MaterialPlanDetailBO;
import com.inventory.middle.application.plan.plan.calculate.bo.MaterialPlanInstanceBO;
import com.inventory.middle.application.plan.plan.calculate.support.formula.Formulas;
import com.inventory.middle.application.plan.plan.calculate.support.formula.factor.FactorHelper;
import com.inventory.middle.application.plan.plan.calculate.support.formula.factor.MaterialFactor;
import com.inventory.middle.application.plan.plan.calculate.support.formula.indicator.Indicators;
import com.inventory.middle.application.plan.plan.calculate.support.generate.processor.MaterialPlanDetailPostProcessor;
import com.inventory.middle.application.plan.plan.config.bo.PlanBO;
import com.inventory.middle.application.plan.plan.config.bo.PlanMaterialParamQueryReqBO;
import com.inventory.middle.application.plan.plan.config.bo.PlanMaterialParameterBO;
import com.inventory.middle.domain.plan.common.enums.PlanMaterialParamPlanTypeEnum;
import com.inventory.middle.application.plan.plan.config.service.PlanConfigService;
import com.inventory.middle.application.service.LogicalPlantQueryService;
import com.inventory.middle.client.plan.dto.inventory.InvPlantBO;
import com.inventory.middle.application.plan.support.InventorySupportService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.inventory.middle.domain.plan.common.enums.MaterialPlanDetailBusinessExtKeyEnum.*;

/**
 * 物料计划详情后置处理-计划类型=调拨场景下，源头调拨仓决策
 *
 * @author Danny.Lee
 * @date 2021/11/3
 */
@Slf4j
@Component
public class MpdTransferDecidePostProcessor implements MaterialPlanDetailPostProcessor {

    @Resource
    private FactorHelper factorHelper;

    @Resource
    private PlanConfigService planConfigService;

    @Resource
    private InventorySupportService inventorySupportService;

    @Resource
    private LogicalPlantQueryService logicalPlantQueryService;

    @Override
    public void postProcess(MaterialPlanInstanceBO materialPlanInstance, MaterialPlanDetailBO detail) {
        try {
            TransPlantDecideResultPack pack = this.decideTransferPlant(detail);
            // 是否调拨处理逻辑
            if (pack.isTransferProcess()) {
                // 无调拨仓数据，标记物料计划详情，添加监控项
                if (pack.hasNoTransPlant()) {
                    // 添加指标监控MONITOR
                    log.warn("detail=[{},{},{},{}] don't support transfer process",
                            detail.getMaterialCode(), detail.getLogicalPlantNo(), detail.getTenantId(), detail.getPlanDate());
                    detail.addBusinessExt(IGNORE_PLAN_ORDER.getCode(), "true");
                    return;
                }

                // 保存调拨逻辑仓 & 调拨提前期
                detail.addBusinessExt(TRANSFER_PLANT.getCode(), pack.getTransPlant().getLogicalPlantNo());
                detail.addBusinessExt(TRANSFER_VENDOR_LEAD_TIME.getCode(), String.valueOf(pack.getTransferVendorLeadTime()));
            }
        } catch (Exception ex) {
            log.error("processor={} process error,detail={}", getClass().getSimpleName(), detail, ex);
        }
    }


    private TransPlantDecideResultPack decideTransferPlant(MaterialPlanDetailBO detail) {
        TransPlantDecideResultPack pack = new TransPlantDecideResultPack();

        MaterialBO material = new MaterialBO();
        material.setTenantId(detail.getTenantId());
        material.setMaterialCode(detail.getMaterialCode());
        material.setLogicalPlantNo(detail.getLogicalPlantNo());
        PlanMaterialParameterBO parameter = this.findMaterialParameter(material);
        // 非调拨场景不考虑调拨仓
        if (!PlanMaterialParamPlanTypeEnum.isTransfer(parameter.getPlanTypeCode())) {
            return pack.of(TransPlantDecideResult.NO_TRANSPLANT);
        }

        // 计划产出量
        BigDecimal planProduceAmount = detail.getIndicators().get(Indicators.PLAN_PRODUCE.getIndicatorCode());
        // 无计划产出时不考虑调拨仓处理
        if (planProduceAmount.compareTo(BigDecimal.ZERO) <= 0) {
            return pack.of(TransPlantDecideResult.NO_TRANSPLANT);
        }

        List<String> logicalPlants = logicalPlantQueryService.list(detail.getTenantId())
                .stream().map(InvPlantBO::getPlantCode).collect(Collectors.toList());

        // 首先验证调拨仓库
        logicalPlants.add(parameter.getTransferLogicalPlantNo());
        Collections.reverse(logicalPlants);

        PlanMaterialParameterBO prefer = null;
        for (String logicalPlant : logicalPlants) {
            // 排除物料所属仓库
            if (StringUtils.equals(logicalPlant, detail.getLogicalPlantNo())) {
                continue;
            }

            material.setLogicalPlantNo(logicalPlant);
            PlanMaterialParameterBO transferParameter = this.findMaterialParameter(material);
            if (null == transferParameter) {
                continue;
            }

            PlanBO plan = planConfigService.queryPlanByMaterial(material);
            // 不存在有效的计划方案时，认为ATP=0，不满足调拨需求
            if (null != plan && Objects.equals(plan.getStatus(), PlanStatusEnum.ON.getCode())) {
                MaterialFactor factor = factorHelper.buildFactor(plan, transferParameter, DateUtils.toLocalDate(detail.getPlanDate()));
                BigDecimal pabAmount = Formulas.formula(Indicators.ATP).apply(factor).value();
                if (pabAmount.compareTo(planProduceAmount) >= 0) {
                    // 可用库存满足则返回该逻辑仓
                    TransPlantDecideResult result = StringUtils.equals(transferParameter.getLogicalPlantNo(), parameter.getLogicalPlantNo()) ?
                            TransPlantDecideResult.EXPECT_MEET : TransPlantDecideResult.UNEXPECT_MEET;
                    return pack.of(result, transferParameter, 0);
                }
            }

            if (null == prefer || transferParameter.getVendorLeadTime() < prefer.getVendorLeadTime()) {
                // 优先选择提前更早的计划参数对应的逻辑仓作为候选
                prefer = transferParameter;
            }
        }
        // 不存在满足调拨仓-说明因配置缺失导致不存在任何逻辑仓支持调拨
        if (null == prefer) {
            return pack.of(TransPlantDecideResult.NONE_MEET_MISS_CONFIG);
        }
        // 可用库存都不满足挑选提前期最早的逻辑仓
        return pack.of(TransPlantDecideResult.NONE_MEET_SELECT, prefer, prefer.getVendorLeadTime());
    }

    private PlanMaterialParameterBO findMaterialParameter(MaterialBO material) {
        PlanMaterialParamQueryReqBO request = new PlanMaterialParamQueryReqBO();
        request.setTenantId(material.getTenantId());
        request.setMaterialCode(material.getMaterialCode());
        request.setLogicalPlantNo(material.getLogicalPlantNo());
        return planConfigService.queryByMaterialCodeAndLogicalPlantNo(request);
    }

    /**
     * 调拨仓确定结果包
     */
    @Getter
    private static class TransPlantDecideResultPack {

        private TransPlantDecideResult result;

        // 调拨仓物料计划参数
        private PlanMaterialParameterBO transPlant;

        // 调拨提前期
        private Integer transferVendorLeadTime;

        public TransPlantDecideResultPack of(TransPlantDecideResult result) {
            this.result = result;
            return this;
        }

        public TransPlantDecideResultPack of(TransPlantDecideResult result, PlanMaterialParameterBO transPlant, Integer vendorLeadTime) {
            this.result = result;
            this.transPlant = transPlant;
            this.transferVendorLeadTime = vendorLeadTime;
            return this;
        }

        public boolean needRecalculateIndicators() {
            return result == TransPlantDecideResult.NONE_MEET_SELECT && (null != transferVendorLeadTime && transferVendorLeadTime > 0);
        }

        public boolean isTransferProcess() {
            return result != TransPlantDecideResult.NO_TRANSPLANT;
        }

        public boolean hasTransPlant() {
            return result == TransPlantDecideResult.EXPECT_MEET ||
                    result == TransPlantDecideResult.UNEXPECT_MEET ||
                    result == TransPlantDecideResult.NONE_MEET_SELECT;
        }

        public boolean hasNoTransPlant() {
            return result == TransPlantDecideResult.NONE_MEET_MISS_CONFIG;
        }

    }

    /**
     * 调拨仓确定结果
     */
    private enum TransPlantDecideResult {
        /**
         * 无需调拨仓
         */
        NO_TRANSPLANT,
        /**
         * 调拨仓满足预期
         */
        EXPECT_MEET,
        /**
         * 非调拨仓满足预期
         */
        UNEXPECT_MEET,
        /**
         * 无仓库满足预期-挑选最短提前去的库存作为调拨库存
         */
        NONE_MEET_SELECT,
        /**
         * 无仓库满足预期-缺失配置导致不存在可用仓库
         */
        NONE_MEET_MISS_CONFIG,
    }
}
