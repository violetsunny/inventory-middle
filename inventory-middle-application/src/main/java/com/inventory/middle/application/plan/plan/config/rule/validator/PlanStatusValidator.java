package com.inventory.middle.application.plan.plan.config.rule.validator;

import com.inventory.middle.domain.plan.common.enums.PlanStatusEnum;
import com.inventory.middle.domain.plan.common.enums.ResponseCodeEnum;
import com.inventory.middle.domain.plan.common.ex.Ex;
import com.inventory.middle.application.plan.plan.config.bo.PlanBO;
import com.inventory.middle.domain.plan.common.rule.IValidator;
import com.inventory.middle.domain.plan.common.rule.ValidateMessage;
import com.inventory.middle.infra.plan.persistence.dao.plan.PlanConfigDao;
import com.inventory.middle.infra.plan.persistence.entity.PlanPO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;


/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 计划状态校验器
 * @date 2021/11/5 14:16
 */
@Component
public class PlanStatusValidator implements IValidator {

    @Resource
    private PlanConfigDao planConfigDao;

    @Override
    public ValidateMessage doValidate(ValidateMessage message) {
        PlanBO planBO = (PlanBO) message.getT();

        PlanPO planPO = planConfigDao.queryPlanById(planBO.getId());
        // 计划方案不存在
        if (Objects.isNull(planPO)){
            throw Ex.of(ResponseCodeEnum.DATA_IS_NULL);
        }

        if (!planPO.getTenantId().equals(planBO.getTenantId())){
            throw Ex.of(ResponseCodeEnum.NO_AUTH);
        }
        // 只有指定物料的计划才校验
        if (planPO.getStatus().equals(PlanStatusEnum.ON.getCode())){
            throw Ex.of(ResponseCodeEnum.P_CREATE_PLAN_MATERIAL_FAIL_PLAN_STATUS_IS_ON);
        }
        return ValidateMessage.builder().t(planBO).e(null).build();
    }
}