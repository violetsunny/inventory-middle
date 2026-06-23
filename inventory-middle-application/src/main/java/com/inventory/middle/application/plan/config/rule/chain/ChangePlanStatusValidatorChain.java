package com.inventory.middle.application.plan.config.rule.chain;

import cn.hutool.core.collection.CollectionUtil;
import com.inventory.middle.domain.plan.common.rule.AbstractValidatorChain;
import com.inventory.middle.domain.plan.common.rule.IValidator;
import com.inventory.middle.domain.plan.common.rule.ValidateMessage;
import com.inventory.middle.application.plan.config.rule.validator.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author caosheng
 * @version 1.0
 * @description: 更新计划状态
 * @date 2021/10/17 17:44
 */
@Component
public class ChangePlanStatusValidatorChain extends AbstractValidatorChain {


    @Resource
    private PlanLogicalPlantInExecValidator planLogicalPlantInExecValidator;

    @Resource
    private PlanMaterialValidator planMaterialValidator;

    @Override
    protected void initChain() {
        validators.add(planLogicalPlantInExecValidator);
        validators.add(planMaterialValidator);
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