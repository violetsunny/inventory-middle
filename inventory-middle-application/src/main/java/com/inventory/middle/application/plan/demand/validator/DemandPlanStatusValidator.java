package com.inventory.middle.application.plan.demand.validator;

import com.inventory.middle.domain.plan.common.enums.ValidatorResultEnum;
import com.inventory.middle.application.plan.demand.bo.DemandPlanUpdateStatusReqBO;
import com.inventory.middle.domain.plan.common.rule.IValidator;
import com.inventory.middle.domain.plan.common.rule.ValidateMessage;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @description: 校验当前状态是否已经是对应的状态
 * @author:Vincent.Xiao
 * @date:2021/9/29 20:56
 */
@Component
@Order(1)
public class DemandPlanStatusValidator implements IValidator {

    @Override
    public ValidateMessage doValidate(ValidateMessage message) {
        DemandPlanUpdateStatusReqBO req = (DemandPlanUpdateStatusReqBO) message.getT();
        if (Objects.equals(req.getStatus(), req.getDemandPlanPO().getStatus())) {
            return ValidateMessage.builder()
                    .t(req)
                    .e(null)
                    .code(ValidatorResultEnum.DEMAND_PLAN_STATUS_VALID.getCode())
                    .message(ValidatorResultEnum.DEMAND_PLAN_STATUS_VALID.getDesc())
                    .build();
        }
        return ValidateMessage.builder()
                .t(req)
                .e(null)
                .success(true)
                .build();
    }
}
