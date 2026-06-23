package com.inventory.middle.application.plan.plan.calculate.convert;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.inventory.middle.domain.plan.common.enums.*;
import com.inventory.middle.infra.plan.util.DateUtils;
import com.inventory.middle.domain.plan.common.bo.MaterialBO;
import com.inventory.middle.application.plan.plan.calculate.bo.MaterialPlanDetailBO;
import com.inventory.middle.application.plan.plan.calculate.bo.MaterialPlanInstanceBO;
import com.inventory.middle.application.plan.plan.calculate.bo.MaterialPlanInstanceWithPlanBO;
import com.inventory.middle.application.plan.plan.calculate.bo.PlanInstanceBO;
import com.inventory.middle.application.plan.plan.calculate.support.formula.factor.BomFactor;
import com.inventory.middle.application.plan.plan.calculate.support.formula.factor.DemandFactor;
import com.inventory.middle.application.plan.plan.calculate.support.formula.factor.ParameterFactor;
import com.inventory.middle.application.plan.plan.config.bo.PlanBO;
import com.inventory.middle.application.plan.plan.config.bo.PlanMaterialParameterBO;
import com.inventory.middle.application.plan.plan.config.enums.*;
import com.inventory.middle.infra.plan.persistence.entity.*;
import com.inventory.middle.infra.plan.persistence.entity.DemandPlanMaterialDetailPO;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.inventory.middle.domain.plan.common.enums.MaterialPlanDetailNsExtKeyEnum.*;
import static com.inventory.middle.application.plan.plan.calculate.support.formula.indicator.Indicators.PLAN_INVEST;
import static com.inventory.middle.application.plan.plan.calculate.support.formula.indicator.Indicators.PLAN_PRODUCE;
import static com.inventory.middle.application.plan.plan.config.enums.CalculateParamsKeyEnum.*;

/**
 * 计划产出转换器
 *
 * @author Danny.Lee
 * @date 2021/10/1
 */
@Mapper(componentModel = "spring", uses = ConvertHelper.class)
public interface PlanGenerateConverter {

    MaterialBO convertMaterial(MaterialPlanInstancePO materialPlanInstance);

    @Mapping(target = "extAttrs", source = "extAttrs", qualifiedByName = {"ConvertHelper", "jsonToObject"})
    MaterialPlanInstanceBO convertMaterialPlanInstance(MaterialPlanInstancePO materialPlanInstance);

    @Mapping(target = "extAttrs", source = "extAttrs", qualifiedByName = {"ConvertHelper", "jsonToObject"})
    MaterialPlanInstanceWithPlanBO convertMaterialPlanInstance(MaterialPlanInstanceWithPlanPO materialPlanInstance);

    default MaterialPlanDetailBO convertMaterialPlanDetail(MaterialPlanDetailPO materialPlanDetail) {
        if (materialPlanDetail == null) {
            return null;
        }

        MaterialPlanDetailBO materialPlanDetailBO = new MaterialPlanDetailBO();

        JSONObject jsonObject = JSONObject.parseObject(materialPlanDetail.getPlanDetail());

        JSONObject indicators = jsonObject.getJSONObject(INDICATORS.getCode());
        materialPlanDetailBO.setIndicators(indicators.toJavaObject(new TypeReference<Map<String, BigDecimal>>() {
        }));
        JSONObject indicatorExtAttrs = jsonObject.getJSONObject(INDICATOR_EXT_ATTRS.getCode());
        if (null != indicatorExtAttrs) {
            materialPlanDetailBO.setIndicatorExtAttrs(indicatorExtAttrs.toJavaObject(new TypeReference<Map<String, JSONObject>>() {
            }));
        }
        JSONObject businessExtAttrs = jsonObject.getJSONObject(BUSINESS_EXT_ATTRS.getCode());
        if (null != businessExtAttrs) {
            materialPlanDetailBO.setBusinessExtAttrs(businessExtAttrs.toJavaObject(new TypeReference<Map<String, String>>() {
            }));
        }
        materialPlanDetailBO.setId(materialPlanDetail.getId());
        materialPlanDetailBO.setMaterialInstanceId(materialPlanDetail.getMaterialInstanceId());
        materialPlanDetailBO.setMaterialCode(materialPlanDetail.getMaterialCode());
        materialPlanDetailBO.setMaterialDesc(materialPlanDetail.getMaterialDesc());
        materialPlanDetailBO.setLogicalPlantNo(materialPlanDetail.getLogicalPlantNo());
        materialPlanDetailBO.setPlanDate(materialPlanDetail.getPlanDate());
        materialPlanDetailBO.setDeleted(materialPlanDetail.getDeleted());
        materialPlanDetailBO.setCreateTime(materialPlanDetail.getCreateTime());
        materialPlanDetailBO.setCreatorId(materialPlanDetail.getCreatorId());
        materialPlanDetailBO.setTenantId(materialPlanDetail.getTenantId());

        return materialPlanDetailBO;
    }

    PlanInstancePO convertPlanInstance(PlanInstanceBO materialPlanInstance);

    PlanInstanceBO convertPlanInstance(PlanInstancePO materialPlanInstance);

    @Mapping(target = "extAttrs", source = "extAttrs", qualifiedByName = {"ConvertHelper", "objectToJson"})
    MaterialPlanInstancePO convertMaterialPlanInstance(MaterialPlanInstanceBO materialPlanInstance);

    List<MaterialPlanInstancePO> convertMaterialPlanInstances(List<MaterialPlanInstanceBO> materialPlanInstances);

    default MaterialPlanDetailPO convertMaterialPlanDetail(MaterialPlanDetailBO materialPlanDetail) {
        {
            if (materialPlanDetail == null) {
                return null;
            }

            MaterialPlanDetailPO materialPlanDetailPO = new MaterialPlanDetailPO();

            JSONObject planDetail = new JSONObject();
            planDetail.put(INDICATORS.getCode(), materialPlanDetail.getIndicators());
            if (MapUtils.isNotEmpty(materialPlanDetail.getIndicatorExtAttrs())) {
                planDetail.put(INDICATOR_EXT_ATTRS.getCode(), materialPlanDetail.getIndicatorExtAttrs());
            }
            if (MapUtils.isNotEmpty(materialPlanDetail.getBusinessExtAttrs())) {
                planDetail.put(BUSINESS_EXT_ATTRS.getCode(), materialPlanDetail.getBusinessExtAttrs());
            }
            materialPlanDetailPO.setPlanDetail(planDetail.toJSONString());

            materialPlanDetailPO.setId(materialPlanDetail.getId());
            materialPlanDetailPO.setMaterialInstanceId(materialPlanDetail.getMaterialInstanceId());
            materialPlanDetailPO.setMaterialCode(materialPlanDetail.getMaterialCode());
            materialPlanDetailPO.setMaterialDesc(materialPlanDetail.getMaterialDesc());
            materialPlanDetailPO.setLogicalPlantNo(materialPlanDetail.getLogicalPlantNo());
            materialPlanDetailPO.setPlanDate(materialPlanDetail.getPlanDate());
            materialPlanDetailPO.setDeleted(materialPlanDetail.getDeleted());
            materialPlanDetailPO.setCreateTime(materialPlanDetail.getCreateTime());
            materialPlanDetailPO.setCreatorId(materialPlanDetail.getCreatorId());
            materialPlanDetailPO.setTenantId(materialPlanDetail.getTenantId());

            return materialPlanDetailPO;
        }
    }

    List<MaterialPlanDetailPO> convertMaterialPlanDetails(List<MaterialPlanDetailBO> materialPlanDetails);

    default DemandFactor convertDemandFactor(List<DemandPlanMaterialDetailPO> materialDemandDetails) {
        DemandFactor demandFactor = new DemandFactor();

        if (CollectionUtils.isNotEmpty(materialDemandDetails)) {
            Map<LocalDate, BigDecimal> orderDemands = Maps.newHashMap();
            Map<LocalDate, BigDecimal> predictDemands = Maps.newHashMap();
            materialDemandDetails.forEach(item -> {
                DemandPlanMaterialDetailTypeEnum demandType = DemandPlanMaterialDetailTypeEnum.of(item.getType());
                if (demandType == DemandPlanMaterialDetailTypeEnum.ORDER) {
                    orderDemands.put(DateUtils.toLocalDate(item.getPlanDate()), BigDecimal.valueOf(item.getAmount()));
                }
                if (demandType == DemandPlanMaterialDetailTypeEnum.PLAN) {
                    predictDemands.put(DateUtils.toLocalDate(item.getPlanDate()), BigDecimal.valueOf(item.getAmount()));
                }
            });
            demandFactor.setMaterialOrderDemands(orderDemands);
            demandFactor.setMaterialPredictDemands(predictDemands);
        }
        return demandFactor;
    }

    default ParameterFactor convertParameterFactor(PlanBO plan, PlanMaterialParameterBO parameter) {
        ParameterFactor parameterFactor = new ParameterFactor();

        parameterFactor.setPlanType(PlanMaterialParamPlanTypeEnum.getByCode(parameter.getPlanTypeCode()));

        String planQuantityCode = MapUtils.getString(plan.getPlanCalculateParams(), PLAN_QUANTITY_STRATEGY.getCode());
        PlanQuantityStrategyEnum planQuantityEnum = PlanQuantityStrategyEnum.getByCode(planQuantityCode);
        String indefiniteQuantityCode = MapUtils.getString(plan.getPlanCalculateParams(), INDEFINITE_QUANTITY_STRATEGY.getCode());
        IndefiniteQuantityStrategyEnum indefiniteQuantityEnum = IndefiniteQuantityStrategyEnum.getByCode(indefiniteQuantityCode);
        String orderStrategyCode = MapUtils.getString(plan.getPlanCalculateParams(), ORDER_STRATEGY.getCode());
        OrderStrategyEnum orderStrategyEnum = OrderStrategyEnum.getByCode(orderStrategyCode);
        parameterFactor.setProduceType(PlanProduceEnum.of(orderStrategyEnum, planQuantityEnum, indefiniteQuantityEnum));

        parameterFactor.setDemandStrategyCode(parameter.getDemandStrategyCode());
        parameterFactor.setVendorLeadTime(parameter.getVendorLeadTime());
        parameterFactor.setDemandTimeFence(parameter.getDemandTimeFence());
        parameterFactor.setPlanningTimeFence(parameter.getPlanningTimeFence());
        parameterFactor.setOrderQuantity(parameter.getOrderQuantity());
        parameterFactor.setOrderCycleTime(parameter.getOrderCycleTime());

        Integer enableLossRateCode = MapUtils.getInteger(plan.getPlanCalculateParams(), CalculateParamsKeyEnum.ENABLE_LOSS_RATE.getCode());
        parameterFactor.setEnableLossRate(EnableLossRateEnum.getByCode(enableLossRateCode) == EnableLossRateEnum.ON);
        Integer enableFinishedRateCode = MapUtils.getInteger(plan.getPlanCalculateParams(), CalculateParamsKeyEnum.ENABLE_FINISHED_RATE.getCode());
        parameterFactor.setEnableFinishedRate(EnableFinishedRateEnum.getByCode(enableFinishedRateCode) == EnableFinishedRateEnum.ON);
        Integer enableSafetyStockCode = MapUtils.getInteger(plan.getPlanCalculateParams(), CalculateParamsKeyEnum.ENABLE_SAFETY_STOCK.getCode());
        parameterFactor.setEnableSafetyStock(EnableSafetyStockEnum.getByCode(enableSafetyStockCode) == EnableSafetyStockEnum.ON);

        if (parameterFactor.getEnableSafetyStock() && !SafetyStockOptionEnum.UNSELECTED.getCode().equals(String.valueOf(parameter.getSafetyStockCalType()))) {
            if (SafetyStockOptionEnum.FROM_FIXED_VALUE.getCode().equals(String.valueOf(parameter.getSafetyStockCalType()))) {
                parameterFactor.setSafetyStock(new BigDecimal(parameter.getSafetyStock()));
            }
            if (SafetyStockOptionEnum.FROM_FORMULA.getCode().equals(String.valueOf(parameter.getSafetyStockCalType()))) {
                final BigDecimal demandStd = new BigDecimal(MapUtils.getString(parameter.getSafetyStockFactors(), SafetyStockFactorsKeyEnum.DEMAND_STD.getCode()));
                final BigDecimal safetyCoefficient = new BigDecimal(MapUtils.getString(parameter.getSafetyStockFactors(), SafetyStockFactorsKeyEnum.SAFETY_COEFFICIENT.getCode()));
                final BigDecimal guaranteeInterval = new BigDecimal(MapUtils.getString(parameter.getSafetyStockFactors(), SafetyStockFactorsKeyEnum.GUARANTEE_INTERVAL.getCode()));
                parameterFactor.setSafetyStock(demandStd.multiply(safetyCoefficient).multiply(guaranteeInterval).setScale(2, RoundingMode.HALF_UP));
            }
        }
        if (null != parameter.getLossRate()) {
            parameterFactor.setLossRate(new BigDecimal(parameter.getLossRate()));
        }
        if (null != parameter.getFinishedRate()) {
            parameterFactor.setFinishedRate(new BigDecimal(parameter.getFinishedRate()));
        }

        return parameterFactor;
    }

    default BomFactor convertBomFactor(Long amount, MaterialPlanInstanceBO materialPlanInstance) {
        BomFactor bomFactor = new BomFactor();
        bomFactor.setAmount(amount);
        Map<LocalDate, BigDecimal> planInvest = Maps.newHashMap();
        Map<LocalDate, BigDecimal> planProduct = Maps.newHashMap();

        materialPlanInstance.getMaterialPlanDetails().forEach((date,detail) -> {
            Map<String, BigDecimal> indicators = Optional.ofNullable(detail.getIndicators()).orElse(Collections.emptyMap());
            planInvest.put(date, Optional.ofNullable(indicators.get(PLAN_INVEST.getIndicatorCode())).orElse(BigDecimal.ZERO));
            planProduct.put(date, Optional.ofNullable(indicators.get(PLAN_PRODUCE.getIndicatorCode())).orElse(BigDecimal.ZERO));
        });

        bomFactor.setCorrelatedPlanInvests(planInvest);
        bomFactor.setCorrelatedPlanProduces(planProduct);

        return bomFactor;
    }

}
