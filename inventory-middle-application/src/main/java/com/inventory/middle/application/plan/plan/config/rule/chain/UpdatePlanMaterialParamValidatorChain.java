package com.inventory.middle.application.plan.plan.config.rule.chain;

import cn.hutool.core.collection.CollectionUtil;
import com.inventory.middle.domain.plan.common.rule.AbstractValidatorChain;
import com.inventory.middle.domain.plan.common.rule.IValidator;
import com.inventory.middle.domain.plan.common.rule.ValidateMessage;
import com.inventory.middle.application.plan.plan.config.rule.validator.UpdatePlanMaterialParamFormatValidator;
import com.inventory.middle.application.plan.plan.config.rule.validator.UpdatePlanMaterialParamLogicalPlantNoValidator;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 更新物料计划参数 校验器链
 * @date 2021/10/9 10:27
 */
@Component
public class UpdatePlanMaterialParamValidatorChain extends AbstractValidatorChain {

    @Resource
    private UpdatePlanMaterialParamFormatValidator updatePlanMaterialParamFormatValidator;

    @Resource
    private UpdatePlanMaterialParamLogicalPlantNoValidator updatePlanMaterialParamLogicalPlantNoValidator;


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

    @Override
    protected void initChain() {
        validators.add(updatePlanMaterialParamFormatValidator);
        validators.add(updatePlanMaterialParamLogicalPlantNoValidator);
    }
}