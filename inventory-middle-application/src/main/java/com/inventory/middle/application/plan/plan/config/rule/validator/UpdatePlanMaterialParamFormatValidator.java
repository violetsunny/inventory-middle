package com.inventory.middle.application.plan.plan.config.rule.validator;

import com.inventory.middle.domain.plan.common.constants.CommonConstants;
import com.inventory.middle.domain.plan.common.enums.DemandStrategyEnum;
import com.inventory.middle.domain.plan.common.enums.ResponseCodeEnum;
import com.inventory.middle.domain.plan.common.ex.Ex;
import com.inventory.middle.domain.plan.common.rule.IValidator;
import com.inventory.middle.domain.plan.common.rule.ValidateMessage;
import com.inventory.middle.application.plan.plan.config.bo.PlanMaterialParameterBO;
import com.inventory.middle.application.plan.plan.config.enums.MaterialParameterDemandTypeEnum;
import com.inventory.middle.domain.plan.common.enums.PlanMaterialParamPlanTypeEnum;
import com.inventory.middle.application.plan.plan.config.enums.SafetyStockFactorsKeyEnum;
import com.inventory.middle.domain.plan.common.enums.SafetyStockOptionEnum;
import com.inventory.middle.infra.plan.persistence.dao.plan.PlanConfigDao;
import com.inventory.middle.infra.plan.persistence.condition.plan.PlanMaterialParamQueryCondition;
import com.inventory.middle.infra.plan.persistence.entity.PlanMaterialParameterPO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 更新计划物料参数 入参格式校验器
 * @date 2021/10/9 10:18
 */
@Component
public class UpdatePlanMaterialParamFormatValidator implements IValidator {

    @Resource
    private PlanConfigDao planConfigDao;

    @Override
    public ValidateMessage doValidate(ValidateMessage message) {
        // 校验入参
        PlanMaterialParameterBO parameter = (PlanMaterialParameterBO) message.getT();
        // 校验返参
        ResponseCodeEnum responseCodeEnum = null;

        // 水平越权校验
        PlanMaterialParameterPO planMaterialParameterPO = planConfigDao.selectPlanMaterialParameterById(parameter.getId());
        if (Objects.isNull(planMaterialParameterPO)) {
            throw Ex.of(ResponseCodeEnum.DATA_IS_NULL);
        }
        if (!planMaterialParameterPO.getTenantId().equals(parameter.getTenantId())) {
            throw Ex.of(ResponseCodeEnum.NO_AUTH);
        }
        // 校验租户Id
        if (StringUtils.isEmpty(parameter.getTenantId())) {
            throw Ex.of(ResponseCodeEnum.TENANT_ID_IS_NULL);
        }
        // 校验操作人Id
        if (StringUtils.isEmpty(parameter.getUserId()) || StringUtils.isEmpty(parameter.getUserName())) {
            throw Ex.of(ResponseCodeEnum.USER_INFO_IS_NULL);
        }
        // 校验计划类型
        Integer planType = parameter.getPlanTypeCode();
        if (Objects.isNull(planType) || Objects.isNull(PlanMaterialParamPlanTypeEnum.getByCode(planType))) {
            throw Ex.of(ResponseCodeEnum.P_UPDATE_PLAN_MATERIAL_PARAM_FAIL_PLAN_TYPE_ERROR);
        }
        // 如果生产类型 必须维护成品率
//        if (PlanMaterialParamPlanTypeEnum.PURCHASE.getCode().equals(planType) && Objects.isNull(parameter.getFinishedRate())) {
//            throw Ex.of(ResponseCodeEnum.PMP_UPDATE_FINISH_RATE_IS_NULL_WHEN_PURCHASE_TYPE);
//        }
        // 如果采购或者是调拨类型 必须维护损耗率
//        if (PlanMaterialParamPlanTypeEnum.isPurchase(planType) || PlanMaterialParamPlanTypeEnum.isTransfer(planType)) {
//            if (Objects.isNull(parameter.getLossRate())) {
//                throw Ex.of(ResponseCodeEnum.PMP_UPDATE_LOSS_RATE_IS_NULL_WHEN_NO_PURCHASE_TYPE);
//            }
//        }
        // 需求响应策略编码
        String demandStrategyCode = parameter.getDemandStrategyCode();
        if (StringUtils.isEmpty(demandStrategyCode) || Objects.isNull(DemandStrategyEnum.getByCode(demandStrategyCode))) {
            throw Ex.of(ResponseCodeEnum.P_UPDATE_PLAN_MATERIAL_PARAM_FAIL_DEMAND_STRATEGY_CODE_ERROR);
        }
        // 如果是生产类型 必须维护需求时区和计划时区
        if (DemandStrategyEnum.MTS13.getCode().equals(demandStrategyCode)) {
            if (Objects.isNull(parameter.getDemandTimeFence()) ||
                    Objects.isNull(parameter.getPlanningTimeFence())) {
                throw Ex.of(ResponseCodeEnum.PMP_UPDATE_TIME_FENCE_IS_NULL_WHEN_MTS13);
            }
        }
        Integer demandType = parameter.getDemandType();
        if(Objects.isNull(demandType) || Objects.isNull(MaterialParameterDemandTypeEnum.of(demandType))){
            throw Ex.of(ResponseCodeEnum.PMP_UPDATE_DEMAND_TYPE_IS_NULL);
        }
        // 提前期校验 必须是正整数
        if (!Objects.isNull(parameter.getVendorLeadTime()) && parameter.getVendorLeadTime() < CommonConstants.INTEGER_ZERO) {
            throw Ex.of(ResponseCodeEnum.P_UPDATE_PLAN_MATERIAL_PARAM_FAIL_VENDOR_LEAD_TIME_MUST_BE_POSITIVE);
        }
        // 需求时区校验
        if (!Objects.isNull(parameter.getDemandTimeFence()) && parameter.getDemandTimeFence() < CommonConstants.INTEGER_ZERO) {
            throw Ex.of(ResponseCodeEnum.P_UPDATE_PLAN_MATERIAL_PARAM_FAIL_DEMAND_TIME_FENCE_MUST_BE_POSITIVE);
        }
        // 计划时区校验
        if (!Objects.isNull(parameter.getPlanningTimeFence()) && parameter.getPlanningTimeFence() < CommonConstants.INTEGER_ZERO) {
            throw Ex.of(ResponseCodeEnum.P_UPDATE_PLAN_MATERIAL_PARAM_FAIL_PLANNING_TIME_FENCE_MUST_BE_POSITIVE);
        }
        // 订购批量校验
        if (!Objects.isNull(parameter.getOrderQuantity()) && parameter.getOrderQuantity() < CommonConstants.INTEGER_ZERO) {
            throw Ex.of(ResponseCodeEnum.P_UPDATE_PLAN_MATERIAL_PARAM_FAIL_ORDER_QUANTITY_MUST_BE_POSITIVE);
        }
        if (Objects.nonNull(parameter.getMinOrderQuantity()) && parameter.getMinOrderQuantity() < CommonConstants.INTEGER_ZERO) {
            throw Ex.of(ResponseCodeEnum.PMP_UPDATE_MIN_ORDER_QUANTITY_MUST_BE_POSITIVE);
        }

        // 订购周期校验
        if (!Objects.isNull(parameter.getOrderCycleTime()) && parameter.getOrderCycleTime() < CommonConstants.INTEGER_ZERO) {
            throw Ex.of(ResponseCodeEnum.P_UPDATE_PLAN_MATERIAL_PARAM_FAIL_ORDER_CYCLE_TIME_MUST_BE_POSITIVE);
        }
        // 安全库存校验
        SafetyStockOptionEnum ssOption = SafetyStockOptionEnum.of(String.valueOf(parameter.getSafetyStockCalType()));
        if(Objects.isNull(ssOption)){
            throw Ex.of(ResponseCodeEnum.PMP_UPDATE_SAFETY_STOCK_RULE_IS_NULL);
        }
        // 安全库存计算方式=设置定值，要求安全库存为非负数
        if (ssOption == SafetyStockOptionEnum.FROM_FIXED_VALUE && Optional.ofNullable(parameter.getSafetyStock()).map(stock -> stock < 0).orElse(Boolean.FALSE)) {
            throw Ex.of(ResponseCodeEnum.PMP_UPDATE_SAFETY_STOCK_FIXED_IS_NULL);
        }
        // 安全库存计算方式=公式计算，要求安全库存计算参数存在
        if (ssOption == SafetyStockOptionEnum.FROM_FORMULA &&
                Arrays.stream(SafetyStockFactorsKeyEnum.values()).anyMatch(key -> !parameter.getSafetyStockFactors().containsKey(key.getCode()))) {
            throw Ex.of(ResponseCodeEnum.PMP_UPDATE_SAFETY_STOCK_FORMULA_IS_NULL);
        }
        // 损耗率检验
        if (!Objects.isNull(parameter.getLossRate())) {
            if (parameter.getLossRate() < CommonConstants.INTEGER_ZERO
                    || parameter.getLossRate() > CommonConstants.MAX_PERCENT_NUMBER) {
                throw Ex.of(ResponseCodeEnum.P_UPDATE_PLAN_MATERIAL_PARAM_FAIL_LOSS_RATE_FORMAT_ERROR);
            }
        }
        // 成品率检验
        if (!Objects.isNull(parameter.getFinishedRate())) {
            if (parameter.getFinishedRate() < CommonConstants.INTEGER_ZERO
                    || parameter.getFinishedRate() > CommonConstants.MAX_PERCENT_NUMBER) {
                throw Ex.of(ResponseCodeEnum.P_UPDATE_PLAN_MATERIAL_PARAM_FAIL_FINISHED_RATE_FORMAT_ERROR);
            }
        }
        // 检查调拨逻辑仓不等于自身逻辑仓
        if (StringUtils.equals(parameter.getTransferLogicalPlantNo(), planMaterialParameterPO.getLogicalPlantNo())) {
            throw Ex.of(ResponseCodeEnum.P_UPDATE_PLAN_MATERIAL_PARAM_FAIL_LOGICAL_PLANT_NO_IS_SAME);
        }
        // 如果采购类型是调拨 ，所填写的调拨源头仓+物料必须在计划参数里维护
        if (PlanMaterialParamPlanTypeEnum.TRANSFER.getCode().equals(planType) && StringUtils.isNotBlank(parameter.getTransferLogicalPlantNo())) {
            PlanMaterialParamQueryCondition planMaterialParamQueryCondition = new PlanMaterialParamQueryCondition();
            planMaterialParamQueryCondition.setTenantId(planMaterialParameterPO.getTenantId());
            planMaterialParamQueryCondition.setLogicalPlantNo(parameter.getTransferLogicalPlantNo());
            planMaterialParamQueryCondition.setMaterialCode(planMaterialParameterPO.getMaterialCode());
            PlanMaterialParameterPO po = planConfigDao.selectByMaterialCodeAndLogicalPlantNo(planMaterialParamQueryCondition);
            if (Objects.isNull(po)) {
                throw Ex.of(ResponseCodeEnum.P_UPDATE_PLAN_MATERIAL_PARAM_FAIL_TRANSFER_MATERIAL_IS_NOT_EXIST);
            }
        }
        return ValidateMessage.builder().t(parameter).e(null).build();
    }
}