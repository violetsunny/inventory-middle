package com.inventory.middle.application.plan.config.rule.validator;

import com.alibaba.fastjson.JSON;
import com.inventory.middle.domain.plan.common.constants.CommonConstants;
import com.inventory.middle.domain.plan.common.enums.DemandStrategyEnum;
import com.inventory.middle.domain.common.constants.ResponseCodeEnum;
import com.inventory.middle.domain.plan.common.rule.IValidator;
import com.inventory.middle.domain.plan.common.rule.ValidateMessage;
import com.inventory.middle.application.plan.config.bo.PlanMaterialParamBatchCreateDetailBO;
import com.inventory.middle.application.plan.config.bo.PlanMaterialParamBatchCreateReqBO;
import com.inventory.middle.application.plan.config.bo.PlanMaterialParameterBO;
import com.inventory.middle.domain.plan.common.enums.PlanMaterialParamPlanTypeEnum;
import com.inventory.middle.application.plan.config.enums.SafetyStockFactorsKeyEnum;
import com.inventory.middle.domain.plan.common.enums.SafetyStockOptionEnum;
import com.inventory.middle.infra.plan.persistence.dao.PlanConfigDao;
import com.inventory.middle.infra.plan.persistence.condition.PlanMaterialParamQueryCondition;
import com.inventory.middle.infra.plan.persistence.entity.PlanMaterialParameterPO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;


import javax.annotation.Resource;
import java.util.*;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 批量新增计划物料参数 参数格式校验器
 * @date 2021/10/8 15:55
 */
@Slf4j
@Component
public class BatchCreatePlanMaterialParamFormatValidator implements IValidator {

    @Resource
    private PlanConfigDao planConfigDao;

    @Override
    public ValidateMessage doValidate(ValidateMessage message) {
        //校验入参
        PlanMaterialParamBatchCreateReqBO reqBO = (PlanMaterialParamBatchCreateReqBO) message.getT();
        log.info("BatchCreatePlanMaterialParamFormatValidator: " + JSON.toJSONString(reqBO));
        // 校验结果
        List<PlanMaterialParamBatchCreateDetailBO> failedDetails = new ArrayList<>();
        if (CollectionUtils.isEmpty(reqBO.getPlanMaterialParamList())) {
            return ValidateMessage.builder().t(reqBO).e(failedDetails).build();
        }

        Iterator<PlanMaterialParameterBO> iterator = reqBO.getPlanMaterialParamList().iterator();
        while (iterator.hasNext()) {
            PlanMaterialParameterBO planMaterialParameterBO = iterator.next();
            // 校验参数格式
            String checkMessage = checkParamFormat(planMaterialParameterBO);
            if (!StringUtils.isEmpty(checkMessage)) {
                PlanMaterialParamBatchCreateDetailBO failedBO = new PlanMaterialParamBatchCreateDetailBO();
                failedBO.setMaterialCode(planMaterialParameterBO.getMaterialCode());
                failedBO.setLogicalPlantNo(planMaterialParameterBO.getLogicalPlantNo());
                failedBO.setCreateMessage(checkMessage);
                failedBO.setIndex(planMaterialParameterBO.getIndex());
                failedDetails.add(failedBO);
                iterator.remove();
            }
        }
        return ValidateMessage.builder().t(reqBO).e(failedDetails).build();
    }

    /**
     * 校验参数格式
     *
     * @param parameter 物料计划参数
     * @return 错误信息, null表示校验通过
     */
    private String checkParamFormat(PlanMaterialParameterBO parameter) {
        // 校验物料code
        if (StringUtils.isEmpty(parameter.getMaterialCode())) {
            return ResponseCodeEnum.P_CREATE_PLAN_MATERIAL_PARAM_FAIL_MATERIAL_CODE_IS_NULL.getDesc();
        }
        // 校验逻辑仓编码
        if (StringUtils.isEmpty(parameter.getLogicalPlantNo())) {
            return ResponseCodeEnum.P_CREATE_PLAN_MATERIAL_PARAM_FAIL_LOGICAL_PLANT_NO_IS_NULL.getDesc();
        }
        // 校验租户Id
        if (StringUtils.isEmpty(parameter.getTenantId())) {
            return ResponseCodeEnum.PLAN_TENANT_ID_IS_NULL.getDesc();
        }
        // 校验物料需求类型
        if(Objects.isNull(parameter.getDemandType())){
            return ResponseCodeEnum.PMP_CREATE_DEMAND_TYPE_IS_NULL.getCode();
        }

        // 校验订货批量
        if (Objects.nonNull(parameter.getOrderQuantity()) && parameter.getOrderQuantity() < CommonConstants.INTEGER_ZERO) {
            return ResponseCodeEnum.PMP_CREATE_ORDER_QUANTITY_MUST_BE_POSITIVE.getCode();
        }
        if (Objects.nonNull(parameter.getMinOrderQuantity()) && parameter.getMinOrderQuantity() < CommonConstants.INTEGER_ZERO) {
            return ResponseCodeEnum.PMP_CREATE_MIN_ORDER_QUANTITY_MUST_BE_POSITIVE.getCode();
        }

        // 校验安全库存
        SafetyStockOptionEnum ssOption = SafetyStockOptionEnum.of(String.valueOf(parameter.getSafetyStockCalType()));
        if(Objects.isNull(ssOption)){
            return ResponseCodeEnum.PMP_CREATE_SAFETY_STOCK_RULE_IS_NULL.getCode();
        }
        // 安全库存计算方式=设置定值，要求安全库存为非负数
        if (ssOption == SafetyStockOptionEnum.FROM_FIXED_VALUE && Optional.ofNullable(parameter.getSafetyStock()).map(stock -> stock < 0).orElse(Boolean.FALSE)) {
            return ResponseCodeEnum.PMP_CREATE_SAFETY_STOCK_FIXED_IS_NULL.getCode();
        }
        // 安全库存计算方式=公式计算，要求安全库存计算参数存在
        if (ssOption == SafetyStockOptionEnum.FROM_FORMULA &&
                Arrays.stream(SafetyStockFactorsKeyEnum.values()).anyMatch(key -> !parameter.getSafetyStockFactors().containsKey(key.getCode()))) {
            return ResponseCodeEnum.PMP_CREATE_SAFETY_STOCK_FORMULA_IS_NULL.getCode();
        }

        // 校验操作人Id
        if (StringUtils.isEmpty(parameter.getUserId()) || StringUtils.isEmpty(parameter.getUserName())) {
            return ResponseCodeEnum.PLAN_USER_INFO_IS_NULL.getDesc();
        }
        // 校验计划类型
        Integer planType = parameter.getPlanTypeCode();
        if (Objects.isNull(planType) || Objects.isNull(PlanMaterialParamPlanTypeEnum.getByCode(planType))) {
            return ResponseCodeEnum.P_CREATE_PLAN_MATERIAL_PARAM_FAIL_PLAN_TYPE_ERROR.getDesc();
        }
        // 检查调拨逻辑仓不等于自身逻辑仓
        if(StringUtils.equals(parameter.getTransferLogicalPlantNo(),parameter.getLogicalPlantNo())){
            return ResponseCodeEnum.P_CREATE_PLAN_MATERIAL_PARAM_FAIL_LOGICAL_PLANT_NO_IS_SAME.getDesc();
        }
        // 如果采购类型是调拨 ，所填写的调拨源头仓+物料必须在计划参数里维护
        if (PlanMaterialParamPlanTypeEnum.TRANSFER.getCode().equals(planType) && StringUtils.isNotBlank(parameter.getTransferLogicalPlantNo())) {
            PlanMaterialParamQueryCondition planMaterialParamQueryCondition=new PlanMaterialParamQueryCondition();
            planMaterialParamQueryCondition.setTenantId(parameter.getTenantId());
            planMaterialParamQueryCondition.setLogicalPlantNo(parameter.getTransferLogicalPlantNo());
            planMaterialParamQueryCondition.setMaterialCode(parameter.getMaterialCode());
            PlanMaterialParameterPO planMaterialParameter = planConfigDao.selectByMaterialCodeAndLogicalPlantNo(planMaterialParamQueryCondition);
            if(Objects.isNull(planMaterialParameter)){
                return ResponseCodeEnum.PMP_CREATE_TRANSFER_MATERIAL_IS_NOT_EXIST.getDesc();
            }
        }

        // 如果生产类型 必须成品率
//        if (PlanMaterialParamPlanTypeEnum.PURCHASE.getCode().equals(planType) && Objects.isNull(parameter.getFinishedRate())) {
//            return ResponseCodeEnum.PMP_CREATE_FINISH_RATE_IS_NULL_WHEN_PURCHASE_TYPE.getDesc();
//        }
        // 如果采购或者是调拨类型 必须维护损耗率
//        if (PlanMaterialParamPlanTypeEnum.isPurchase(planType) || PlanMaterialParamPlanTypeEnum.isTransfer(planType)) {
//            if (Objects.isNull(parameter.getLossRate())) {
//                return ResponseCodeEnum.PMP_CREATE_LOSS_RATE_IS_NULL_WHEN_NO_PURCHASE_TYPE.getDesc();
//            }
//        }

        // 需求响应策略编码
        String demandStrategyCode = parameter.getDemandStrategyCode();
        if (StringUtils.isEmpty(demandStrategyCode) || Objects.isNull(DemandStrategyEnum.getByCode(demandStrategyCode))) {
            return ResponseCodeEnum.P_CREATE_PLAN_MATERIAL_PARAM_FAIL_DEMAND_STRATEGY_CODE_ERROR.getDesc();
        }
        // 如果是生产类型 必须维护需求时区和计划时区
        if (DemandStrategyEnum.MTS13.getCode().equals(demandStrategyCode)) {
            if (Objects.isNull(parameter.getDemandTimeFence()) ||
                    Objects.isNull(parameter.getPlanningTimeFence())) {
                return ResponseCodeEnum.PMP_CREATE_TIME_FENCE_IS_NULL_WHEN_MTS13.getDesc();
            }
        }
        return null;
    }
}