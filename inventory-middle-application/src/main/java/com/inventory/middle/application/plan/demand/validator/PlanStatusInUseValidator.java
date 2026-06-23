package com.inventory.middle.application.plan.demand.validator;

import com.inventory.middle.application.plan.demand.bo.DemandPlanUpdateStatusReqBO;
import com.inventory.middle.domain.plan.common.rule.IValidator;
import com.inventory.middle.domain.plan.common.rule.ValidateMessage;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 置为无效时，校验是否有进行中的计划
 * <p>
 * 迁移自 com.enn.plan.management.core.demand.rule.validator.PlanStatusInUseValidator
 * 校验逻辑已注释，直接返回成功。
 * </p>
 */
@Component
@Order(3)
public class PlanStatusInUseValidator implements IValidator {

    @Override
    public ValidateMessage doValidate(ValidateMessage message) {
        DemandPlanUpdateStatusReqBO req = (DemandPlanUpdateStatusReqBO) message.getT();
        // 20220509 需求，不再校验
        return ValidateMessage.builder().t(req).success(true).build();
    }
}
