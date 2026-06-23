package com.inventory.middle.application.plan.config.rule.validator;

import com.inventory.middle.domain.plan.common.constants.CommonConstants;
import com.inventory.middle.application.plan.config.enums.CalculateParamsKeyEnum;
import com.inventory.middle.domain.plan.common.enums.ResponseCodeEnum;
import com.inventory.middle.domain.plan.common.ex.Ex;
import com.inventory.middle.domain.plan.common.ex.SpmException;
import com.inventory.middle.domain.plan.common.rule.IValidator;
import com.inventory.middle.domain.plan.common.rule.ValidateMessage;
import com.inventory.middle.application.plan.config.bo.PlanBO;
import com.inventory.middle.application.plan.config.enums.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.Objects;

import static com.inventory.middle.application.plan.config.enums.CalculateParamsKeyEnum.*;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 保存计划方案 参数格式校验器
 * @date 2021/10/13 9:39
 */
@Component
public class PlanFormatValidator implements IValidator {

    @Override
    public ValidateMessage doValidate(ValidateMessage message) {
        // 校验入参
        PlanBO planBO = (PlanBO) message.getT();
        // 校验租户Id
        if (StringUtils.isEmpty(planBO.getTenantId())){
            throw new SpmException(ResponseCodeEnum.TENANT_ID_IS_NULL.getCode(), ResponseCodeEnum.TENANT_ID_IS_NULL.getDesc());
        }
        // 校验操作人Id
        if (StringUtils.isEmpty(planBO.getUserName()) || StringUtils.isEmpty(planBO.getUserId())){
            throw new SpmException(ResponseCodeEnum.USER_INFO_IS_NULL.getCode(), ResponseCodeEnum.USER_INFO_IS_NULL.getDesc());
        }
        // 校验逻辑仓
        if (CollectionUtils.isEmpty(planBO.getCoverLogicalPlantNos())){
            throw new SpmException(ResponseCodeEnum.P_SAVE_PLAN_FAIL_COVER_LOGICAL_PLANT_IS_NULL.getCode(), ResponseCodeEnum.P_SAVE_PLAN_FAIL_COVER_LOGICAL_PLANT_IS_NULL.getDesc());
        }
        // 校验计算范围
        if (Objects.isNull(planBO.getCoverMaterialType())){
            throw new SpmException(ResponseCodeEnum.P_SAVE_PLAN_FAIL_COVER_MATERIAL_TYPE_IS_NULL.getCode(), ResponseCodeEnum.P_SAVE_PLAN_FAIL_COVER_MATERIAL_TYPE_IS_NULL.getDesc());
        }
        // 校验计划展望期
        if (Objects.isNull(planBO.getPlanHorizon()) || planBO.getPlanHorizon() <= CommonConstants.INTEGER_ZERO || planBO.getPlanHorizon() > CommonConstants.INTEGER_180) {
            throw new SpmException(ResponseCodeEnum.P_SAVE_PLAN_FAIL_PLAN_HORIZON_ERROR.getCode(), ResponseCodeEnum.P_SAVE_PLAN_FAIL_PLAN_HORIZON_ERROR.getDesc());
        }
        // 校验计划开始时间
//        if (Objects.isNull(planBO.getPlanStartTime()) || planBO.getPlanStartTime().before(DateUtils.getEndOfDay(new Date()))){
//            return ValidateMessage.builder().t(planBO).e(ResponseCodeEnum.P_SAVE_PLAN_FAIL_PLAN_START_TIME_ERROR).build();
//        }
        // 校验计划类型
        if (Objects.isNull(planBO.getPlanType()) || Objects.isNull(PlanExecuteTypeEnum.of(planBO.getPlanType()))){
            throw Ex.of(ResponseCodeEnum.P_SAVE_FAIL_PLAN_TYPE_ERROR);
        }
        // 校验需求文件
//        if (CollectionUtils.isEmpty(planBO.getDemandPlanIds())){
//            return ValidateMessage.builder().t(planBO).e(ResponseCodeEnum.P_SAVE_PLAN_FAIL_DEMAND_PLAN_FILE_IS_NULL).build();
//        }
        // 校验计算参数
        Map<String, Object> planCalculateParams = planBO.getPlanCalculateParams();
        if (Objects.isNull(planCalculateParams)){
            throw new SpmException(ResponseCodeEnum.P_SAVE_PLAN_FAIL_PLAN_CALCULATE_PARAMS_IS_NULL.getCode(), ResponseCodeEnum.P_SAVE_PLAN_FAIL_PLAN_CALCULATE_PARAMS_IS_NULL.getDesc());
        }
        // 校验订货策略
//        String orderStrategy = (String)planCalculateParams.get(CalculateParamsKeyEnum.ORDER_STRATEGY.getCode());
//        if (Objects.isNull(orderStrategy) || Objects.isNull(OrderStrategyEnum.getByCode(orderStrategy))){
//            throw new SpmException(ResponseCodeEnum.P_SAVE_PLAN_FAIL_ORDER_STRATEGY_ERROR.getCode(), ResponseCodeEnum.P_SAVE_PLAN_FAIL_ORDER_STRATEGY_ERROR.getDesc());
//        }
        // 校验计划订单量策略
//        String planQuantityStrategy = (String)planCalculateParams.get(CalculateParamsKeyEnum.PLAN_QUANTITY_STRATEGY.getCode());
//        if (Objects.isNull(planQuantityStrategy) || Objects.isNull(PlanQuantityStrategyEnum.getByCode(planQuantityStrategy))){
//            throw new SpmException(ResponseCodeEnum.P_SAVE_PLAN_FAIL_PLAN_QUANTITY_STRATEGY_ERROR.getCode(), ResponseCodeEnum.P_SAVE_PLAN_FAIL_PLAN_QUANTITY_STRATEGY_ERROR.getDesc());
//        }

        // 校验订货计算规则
        String orderCalRule = (String) planCalculateParams.get(ORDER_CAL_RULE.getCode());
        if (Objects.isNull(orderCalRule) || Objects.isNull(OrderCalRuleEnum.of(orderCalRule))) {
            throw Ex.of(ResponseCodeEnum.P_SAVE_FAIL_ORDER_CALCULATE_RULE_ERROR);
        }

        // 订货间隔
        Integer orderInterval = (Integer) planCalculateParams.get(ORDER_CAL_INTERVAL.getCode());
        if (Objects.isNull(orderInterval)) {
            // 设置订货间隔默认=1
            planCalculateParams.put(ORDER_CAL_INTERVAL.getCode(), 1);
        }

        // 校验订货量类型
        String orderProduceType = (String) planCalculateParams.get(ORDER_PRODUCE_TYPE.getCode());
        if (Objects.isNull(orderProduceType) || Objects.isNull(OrderProduceTypeEnum.of(orderProduceType))) {
            throw Ex.of(ResponseCodeEnum.P_SAVE_FAIL_ORDER_PRODUCT_TYPE_ERROR);
        }

        // 计划执行类型-订货计算规则-订货量类型映射规则校验
        // 手动-净需求-不定量#根据净需求
        // 自动-净需求-不定量#根据净需求
        // 自动-周期订货-定量
        // 自动-周期订货-不定量#根据目标差额
        // 自动-补货点-定量
        // 自动-补货点-不定量#根据目标差额
        PlanExecuteTypeEnum planExecuteTypeEnum = PlanExecuteTypeEnum.of(planBO.getPlanType());
        OrderCalRuleEnum orderCalRuleEnum = OrderCalRuleEnum.of(orderCalRule);
        OrderProduceTypeEnum orderProduceTypeEnum = OrderProduceTypeEnum.of(orderProduceType);
        if (!planExecuteTypeEnum.rules().contains(orderCalRuleEnum)) {
            throw Ex.of(ResponseCodeEnum.P_SAVE_FAIL_ORDER_RULE_NOT_BELONG_TYPE);
        }
        if (!orderCalRuleEnum.produceTypes().contains(orderProduceTypeEnum)) {
            throw Ex.of(ResponseCodeEnum.P_SAVE_FAIL_ORDER_PRODUCT_NOT_BELONG_RULE);
        }


        // 选择订单量策略为不定量时 需要检查不定量策略是否选择了 净需求规则或者目标差额规则
//        if (planQuantityStrategy.equals(PlanQuantityStrategyEnum.INDEFINITE_QUANTITY.getCode())){
//            String indefiniteQuantityStrategy = (String)planCalculateParams.get(CalculateParamsKeyEnum.INDEFINITE_QUANTITY_STRATEGY.getCode());
//            if (Objects.isNull(indefiniteQuantityStrategy) ||
//                    Objects.isNull(IndefiniteQuantityStrategyEnum.getByCode(indefiniteQuantityStrategy))){
//                throw new SpmException(ResponseCodeEnum.P_SAVE_PLAN_FAIL_INDEFINITE_QUANTITY_STRATEGY_ERROR.getCode(), ResponseCodeEnum.P_SAVE_PLAN_FAIL_INDEFINITE_QUANTITY_STRATEGY_ERROR.getDesc());
//            }
//        }
        // 如果传了损耗率 校验损耗率格式
        Integer enableLossRate = (Integer)planCalculateParams.get(CalculateParamsKeyEnum.ENABLE_LOSS_RATE.getCode());
        if (!Objects.isNull(enableLossRate) && Objects.isNull(EnableLossRateEnum.getByCode(enableLossRate))){
            throw new SpmException(ResponseCodeEnum.P_SAVE_PLAN_FAIL_ENABLE_LOSS_RATE_ERROR.getCode(), ResponseCodeEnum.P_SAVE_PLAN_FAIL_ENABLE_LOSS_RATE_ERROR.getDesc());
        }
        // 如果传了成品率 校验成品率格式
        Integer enableFinishedRate = (Integer)planCalculateParams.get(CalculateParamsKeyEnum.ENABLE_FINISHED_RATE.getCode());
        if (!Objects.isNull(enableFinishedRate) && Objects.isNull(EnableFinishedRateEnum.getByCode(enableFinishedRate))){
            throw new SpmException(ResponseCodeEnum.P_SAVE_PLAN_FAIL_ENABLE_FINISHED_RATE_ERROR.getCode(), ResponseCodeEnum.P_SAVE_PLAN_FAIL_ENABLE_FINISHED_RATE_ERROR.getDesc());
        }
        // 如果传了损耗率 校验损耗率格式
        Integer enableSafetyStock = (Integer)planCalculateParams.get(CalculateParamsKeyEnum.ENABLE_SAFETY_STOCK.getCode());
        if (!Objects.isNull(enableSafetyStock) && Objects.isNull(EnableLossRateEnum.getByCode(enableSafetyStock))){
            throw new SpmException(ResponseCodeEnum.P_SAVE_PLAN_FAIL_ENABLE_SAFETY_STOCK_ERROR.getCode(), ResponseCodeEnum.P_SAVE_PLAN_FAIL_ENABLE_SAFETY_STOCK_ERROR.getDesc());
        }
        // 校验投放参数
        Map<String, Object> planDeliveryParams = planBO.getPlanDeliveryParams();
        if (Objects.isNull(planDeliveryParams)){
            throw new SpmException(ResponseCodeEnum.P_SAVE_PLAN_FAIL_PLAN_DELIVERY_PARAMS_IS_NULL.getCode(), ResponseCodeEnum.P_SAVE_PLAN_FAIL_PLAN_DELIVERY_PARAMS_IS_NULL.getDesc());
        }
        // 采购人校验
//        String purchaserId = (String) planDeliveryParams.get(DeliveryParamsKeyEnum.PURCHASER_ID.getCode());
//        if (Objects.isNull(purchaserId)) {
//            throw Ex.of(ResponseCodeEnum.P_SAVE_FAIL_PURCHASER_IS_NULL);
//        }
//        String purchaserName = (String) planDeliveryParams.get(DeliveryParamsKeyEnum.PURCHASER_NAME.getCode());
//        if (Objects.isNull(purchaserName)) {
//            throw Ex.of(ResponseCodeEnum.P_SAVE_FAIL_PURCHASER_IS_NULL);
//        }
        // 方案描述校验
        if (StringUtils.isEmpty(planBO.getPlanDesc()) || planBO.getPlanDesc().length() > CommonConstants.MAX_LENGTH_40){
            throw new SpmException(ResponseCodeEnum.P_SAVE_PLAN_FAIL_PLAN_DESC_ERROR.getCode(), ResponseCodeEnum.P_SAVE_PLAN_FAIL_PLAN_DESC_ERROR.getDesc());
        }
        return ValidateMessage.builder().t(planBO).e(null).build();
    }
}