package com.inventory.middle.application.plan.calculate.support.generate.validator;

import com.inventory.middle.domain.plan.common.enums.PlanStatusEnum;
import com.inventory.middle.domain.common.constants.ResponseCodeEnum;
import com.inventory.middle.infra.plan.util.DateUtils;
import com.inventory.middle.application.plan.config.bo.PlanBO;
import com.inventory.middle.application.plan.calculate.bo.PlanGenerateRequestBO;
import com.inventory.middle.domain.plan.common.rule.IValidator;
import com.inventory.middle.domain.plan.common.rule.ValidateMessage;
import com.inventory.middle.application.plan.config.service.PlanConfigService;
import com.inventory.middle.application.plan.calculate.support.generate.validator.model.PlanGeneValidateResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;

/**
 * 计划方案有效性校验
 *
 * @author Danny.Lee
 * @date 2021/11/1
 */
@Slf4j
@Component
public class PlanGeneConfigEffectiveValidator implements IValidator {

    @Resource
    private PlanConfigService planConfigService;


    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public ValidateMessage doValidate(ValidateMessage message) {
        ValidateMessage validateMessage = ValidateMessage.builder()
                .t(message.getT())
                .success(Boolean.TRUE)
                .build();

        // 请求参数
        final PlanGenerateRequestBO request = (PlanGenerateRequestBO) message.getT();
        final Long planId = request.getPlanId();

        PlanBO plan = planConfigService.queryPlanById(planId, request.getTenantId());

        // 计划方案有效性判断
        if (null == plan || PlanStatusEnum.OFF.getCode().equals(plan.getStatus())) {
            return validateMessage
                    .setSuccess(Boolean.FALSE)
                    .setE(PlanGeneValidateResponse.of(ResponseCodeEnum.PI_PARAMS_NOT_EFFECTIVE_PLAN));
        }

        // 计划日期有效性
        final LocalDate nowDate = LocalDate.now();
        final LocalDate planStartDate = DateUtils.toLocalDate(plan.getPlanStartTime());
        final LocalDate planEntranceDate = planStartDate.minusDays(1);
        final LocalDate planEndDate = DateUtils.toLocalDate(plan.getPlanEndTime());
        // 计划完结时间早于当前日期
        // 计划触发开始时间晚于当前日期
        if (!planEndDate.isAfter(nowDate) || nowDate.isBefore(planEntranceDate)) {
            return validateMessage
                    .setSuccess(Boolean.FALSE)
                    .setE(PlanGeneValidateResponse.of(ResponseCodeEnum.PI_PARAMS_EXCEED_PLAN_RANGE));
        }

        // 上下文填充计划配置
        return validateMessage.setE(new PlanGeneValidateResponse().setPlan(plan));
    }


}
