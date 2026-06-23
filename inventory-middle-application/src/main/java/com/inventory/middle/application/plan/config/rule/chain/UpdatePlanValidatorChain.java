package com.inventory.middle.application.plan.config.rule.chain;

import cn.hutool.core.collection.CollectionUtil;
import com.inventory.middle.domain.plan.common.rule.AbstractValidatorChain;
import com.inventory.middle.domain.plan.common.rule.IValidator;
import com.inventory.middle.domain.plan.common.rule.ValidateMessage;
import com.inventory.middle.application.plan.config.rule.validator.PlanFormatValidator;
import com.inventory.middle.application.plan.config.rule.validator.PlanLogicalPlantInExecValidator;
import com.inventory.middle.application.plan.config.rule.validator.PlanStatusValidator;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 更新计划方案校验器Chain
 * @date 2021/11/5 14:25
 */
@Component
public class UpdatePlanValidatorChain extends AbstractValidatorChain {

    @Resource
    private PlanStatusValidator planStatusValidator;

    @Resource
    private PlanFormatValidator planFormatValidator;

    @Resource
    private PlanLogicalPlantInExecValidator planLogicalPlantInExecValidator;


    @Override
    protected void initChain() {
        validators.add(planStatusValidator);
        validators.add(planFormatValidator);
        validators.add(planLogicalPlantInExecValidator);
    }

    @Override
    public ValidateMessage doProcess(ValidateMessage originalMessage) {
        // 循环校验
        if (CollectionUtil.isNotEmpty(validators)) {
            for (IValidator validator : validators) {
                // 开始校验
                validator.doValidate(originalMessage);
            }
        }
        return null;
    }
}