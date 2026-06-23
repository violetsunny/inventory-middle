package com.inventory.middle.application.plan.plan.calculate.support.generate.validator;

import com.inventory.middle.domain.plan.common.enums.PlanStatusEnum;
import com.inventory.middle.domain.plan.common.enums.ResponseCodeEnum;
import com.inventory.middle.domain.plan.common.rule.IValidator;
import com.inventory.middle.domain.plan.common.rule.ValidateMessage;
import com.inventory.middle.infra.plan.util.DateUtils;
import com.inventory.middle.domain.plan.common.bo.MaterialBO;
import com.inventory.middle.application.plan.plan.calculate.support.generate.validator.model.MaterialSupportGeneValidateRequest;
import com.inventory.middle.application.plan.plan.calculate.support.generate.validator.model.MaterialSupportGeneValidateResponse;
import com.inventory.middle.application.plan.plan.calculate.support.generate.validator.model.PlanGeneValidateResponse;
import com.inventory.middle.application.plan.plan.config.bo.PlanBO;
import com.inventory.middle.application.plan.plan.config.bo.PlanMaterialParamQueryReqBO;
import com.inventory.middle.application.plan.plan.config.bo.PlanMaterialParameterBO;
import com.inventory.middle.application.plan.plan.config.enums.OrderCalRuleEnum;
import com.inventory.middle.application.plan.plan.config.enums.PlanExecuteTypeEnum;
import com.inventory.middle.application.plan.plan.config.service.PlanConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;

import static com.inventory.middle.application.plan.plan.config.enums.CalculateParamsKeyEnum.ORDER_CAL_RULE;

/**
 * 物料计划配置有效性
 *
 * @author Danny.Lee
 * @date 2021/11/2
 */
@Slf4j
@Component
public class MaterialSupportGeneConfigEffectiveValidator implements IValidator {

    @Resource
    private PlanConfigService planConfigService;

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public ValidateMessage doValidate(ValidateMessage message) {

        ValidateMessage validateMessage = ValidateMessage.builder()
                .t(message.getT())
                .success(Boolean.TRUE)
                .build();

        MaterialSupportGeneValidateRequest request = (MaterialSupportGeneValidateRequest) message.getT();

        MaterialBO material = new MaterialBO();
        material.setTenantId(request.getTenantId());
        material.setMaterialCode(request.getMaterialCode());
        material.setLogicalPlantNo(request.getLogicalPlantNo());

        PlanBO plan = planConfigService.queryPlanByMaterial(material);

        // 计划方案有效性
        if (!isEffectivePlan(plan)) {
            return validateMessage
                    .setSuccess(Boolean.FALSE)
                    .setE(MaterialSupportGeneValidateResponse.of(ResponseCodeEnum.PI_PARAMS_NOT_EFFECTIVE_PLAN));
        }

        // 计划日期有效性
        final LocalDate nowDate = LocalDate.now();
        final LocalDate planStartDate = DateUtils.toLocalDate(plan.getPlanStartTime());
        final LocalDate planEntranceDate = planStartDate.minusDays(1);
        final LocalDate planEndDate = DateUtils.toLocalDate(plan.getPlanEndTime());

        // 计划完结时间早于当前日期
        // 计划触发开始时间晚于当前日期
        if (planEndDate.isBefore(nowDate) || nowDate.isBefore(planEntranceDate)) {
            return validateMessage
                    .setSuccess(Boolean.FALSE)
                    .setE(MaterialSupportGeneValidateResponse.of(ResponseCodeEnum.PI_PARAMS_EXCEED_PLAN_RANGE));
        }

        // 计划日期在计划方案起止日期范围内
        if (null != request.getPlanDate()) {
            final LocalDate planStartDateToUse = LocalDate.now().isBefore(planStartDate) ? planStartDate : nowDate;
            if (request.getPlanDate().isBefore(planStartDateToUse) || request.getPlanDate().isAfter(planEndDate)) {
                return validateMessage
                        .setSuccess(Boolean.FALSE)
                        .setE(MaterialSupportGeneValidateResponse.of(ResponseCodeEnum.PI_PARAMS_EXCEED_PLAN_RANGE));
            }
        }

        PlanMaterialParameterBO parameter = this.findMaterialParameter(material);
        if (null == parameter) {
            return validateMessage
                    .setSuccess(Boolean.FALSE)
                    .setE(PlanGeneValidateResponse.of(ResponseCodeEnum.PI_PARAMS_NOT_EFFECTIVE_PARAMETER));

        }

        // 上下文填充计划配置
        return validateMessage.setE(new MaterialSupportGeneValidateResponse()
                .setPlan(plan)
                .setParameter(parameter));
    }

    private PlanMaterialParameterBO findMaterialParameter(MaterialBO material) {
        PlanMaterialParamQueryReqBO request = new PlanMaterialParamQueryReqBO();
        request.setTenantId(material.getTenantId());
        request.setMaterialCode(material.getMaterialCode());
        request.setLogicalPlantNo(material.getLogicalPlantNo());
        return planConfigService.queryByMaterialCodeAndLogicalPlantNo(request);
    }

    private boolean isEffectivePlan(PlanBO plan) {
        if (null == plan) {
            return false;
        }
        if (PlanStatusEnum.OFF.getCode().equals(plan.getStatus())) {
            return false;
        }
        if (!PlanExecuteTypeEnum.AUTOMATIC.getCode().equals(plan.getPlanType())) {
            return false;
        }
        String orderCalRule = MapUtils.getString(plan.getPlanCalculateParams(), ORDER_CAL_RULE.getCode());
        if (OrderCalRuleEnum.isCycleTime(orderCalRule)) {
            return false;
        }
        return true;
    }
}
