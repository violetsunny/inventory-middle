package com.inventory.middle.application.plan.plan.calculate.support.generate.validator;

import com.inventory.middle.domain.plan.common.enums.InstanceStatusEnum;
import com.inventory.middle.domain.plan.common.enums.ResponseCodeEnum;
import com.inventory.middle.application.plan.plan.calculate.bo.PlanGenerateRequestBO;
import com.inventory.middle.domain.plan.common.rule.IValidator;
import com.inventory.middle.domain.plan.common.rule.ValidateMessage;
import com.inventory.middle.application.plan.plan.calculate.support.generate.PlanGeneType;
import com.inventory.middle.application.plan.plan.calculate.support.generate.validator.model.PlanGeneValidateResponse;
import com.inventory.middle.infra.plan.persistence.dao.plan.PlanInstanceDao;
import com.inventory.middle.infra.plan.persistence.entity.PlanInstancePO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 计划产出类型校验器
 * <pre>
 *     1. JOB-无校验
 *     2. MANUAL-手工触发异步返回，会要求未完结的不允许再次发起
 *     3. MESSAGE-每小时最多产出一份
 * </pre>
 *
 * @author Danny.Lee
 * @date 2021/11/1
 */
@Slf4j
@Component
public class PlanGeneTypeValidator implements IValidator {

    @Resource
    private PlanInstanceDao planInstanceDao;

    @Override
    @SuppressWarnings({"rawtypes","unchecked"})
    public ValidateMessage doValidate(ValidateMessage message) {
        ValidateMessage validateMessage = ValidateMessage.builder()
                .t(message.getT())
                .success(Boolean.TRUE)
                .build();

        // 请求参数
        final PlanGenerateRequestBO request = (PlanGenerateRequestBO) message.getT();
        final Long planId = request.getPlanId();
        final PlanGeneType planGenerateType = request.getGenerateType();

        // 手工调度查询上次调度结果是否完成
        if (planGenerateType == PlanGeneType.MANUAL) {
            PlanInstancePO planInstance = planInstanceDao.selectLatestByPlanId(planId);
            if (null != planInstance && !InstanceStatusEnum.isCompleted(planInstance.getStatus())) {
                return validateMessage
                        .setSuccess(Boolean.FALSE)
                        .setE(PlanGeneValidateResponse.of(ResponseCodeEnum.PI_EXECUTE_MANUAL_UNCOMPLETED));
            }
        }
        return validateMessage;
    }
}
