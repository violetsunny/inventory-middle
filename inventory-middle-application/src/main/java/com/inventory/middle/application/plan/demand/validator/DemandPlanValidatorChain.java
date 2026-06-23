package com.inventory.middle.application.plan.demand.validator;

import cn.hutool.core.collection.CollectionUtil;
import com.inventory.middle.domain.plan.common.ex.Ex;
import com.inventory.middle.application.plan.demand.validator.DemandPlanStatusValidator;
import com.inventory.middle.application.plan.demand.validator.PlanStatusInUseValidator;
import com.inventory.middle.application.plan.demand.validator.PlanStatusMaterialValidator;
import com.inventory.middle.domain.plan.common.rule.AbstractValidatorChain;
import com.inventory.middle.domain.plan.common.rule.IValidator;
import com.inventory.middle.domain.plan.common.rule.ValidateMessage;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @description: 需求计划校验器
 * @author:Vincent.Xiao
 * @date:2021/12/28 10:08
 */
@Component
public class DemandPlanValidatorChain  extends AbstractValidatorChain {

    @Resource
    private DemandPlanStatusValidator demandPlanStatusValidator;

    @Resource
    private PlanStatusMaterialValidator planStatusMaterialValidator;

    @Resource
    private PlanStatusInUseValidator planStatusInUseValidator;


    @Override
    protected void initChain() {
        validators.add(demandPlanStatusValidator);
        validators.add(planStatusMaterialValidator);
        validators.add(planStatusInUseValidator);
    }

    @Override
    public ValidateMessage doProcess(ValidateMessage originalMessage) {
        // 循环校验
        if (CollectionUtil.isNotEmpty(validators)) {
            for (IValidator validator : validators) {
                // 开始校验
                ValidateMessage result =  validator.doValidate(originalMessage);
                if (!result.isSuccess()) {
                    throw Ex.of(result.getCode(), result.getMessage());
                }
            }
        }
        return null;
    }
}
