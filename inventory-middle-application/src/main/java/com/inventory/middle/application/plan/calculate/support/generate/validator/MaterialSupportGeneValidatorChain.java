package com.inventory.middle.application.plan.calculate.support.generate.validator;

import cn.hutool.core.collection.CollectionUtil;
import com.inventory.middle.domain.plan.common.rule.AbstractValidatorChain;
import com.inventory.middle.domain.plan.common.rule.IValidator;
import com.inventory.middle.domain.plan.common.rule.ValidateMessage;
import com.inventory.middle.application.plan.calculate.support.generate.validator.model.MaterialSupportGeneValidateResponse;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 物料计划产出准入判断校验链
 *
 * @author Danny.Lee
 * @date 2021/11/1
 */
@Component
public class MaterialSupportGeneValidatorChain extends AbstractValidatorChain {

    @Resource
    private IValidator materialSupportGeneConfigEffectiveValidator;

    @Resource
    private IValidator materialSupportGeneIntervalValidator;

    @Resource
    private IValidator materialSupportGeneNetRequirementValidator;

    @Resource
    private IValidator materialSupportGeneReplenishmentPointValidator;


    @Override
    protected void initChain() {
        validators.add(materialSupportGeneConfigEffectiveValidator);
        validators.add(materialSupportGeneIntervalValidator);
        validators.add(materialSupportGeneNetRequirementValidator);
        validators.add(materialSupportGeneReplenishmentPointValidator);
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public ValidateMessage doProcess(ValidateMessage originalMessage) {

        MaterialSupportGeneValidateResponse response = new MaterialSupportGeneValidateResponse();
        // 作为上下文传递
        originalMessage.setE(response);

        if (CollectionUtil.isNotEmpty(validators)) {
            for (IValidator validator : validators) {

                ValidateMessage validateMessage = validator.doValidate(originalMessage);

                // 合并校验响应模型
                response.merge((MaterialSupportGeneValidateResponse) validateMessage.getE());
                if (!validateMessage.isSuccess()) {
                    break;
                }
            }
        }

        return ValidateMessage.builder()
                .t(originalMessage.getT())
                .e(response)
                .success(null == response.getResponseCode())
                .build();
    }
}
