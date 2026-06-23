package com.inventory.middle.application.plan.plan.calculate.support.generate.validator;

import cn.hutool.core.collection.CollectionUtil;
import com.inventory.middle.domain.plan.common.rule.AbstractValidatorChain;
import com.inventory.middle.domain.plan.common.rule.IValidator;
import com.inventory.middle.domain.plan.common.rule.ValidateMessage;
import com.inventory.middle.application.plan.plan.calculate.support.generate.validator.model.PlanGeneValidateResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 计划实例产出校验链
 *
 * @author Danny.Lee
 * @date 2021/11/1
 */
@Slf4j
@Component
public class PlanGeneValidatorChain extends AbstractValidatorChain {

    @Resource
    private IValidator planGeneConfigEffectiveValidator;

    @Resource
    private IValidator planGeneTypeValidator;

    @Resource
    private IValidator planGeneMaterialValidator;

    @Override
    protected void initChain() {
        // 校验器之间存在依赖关系,请勿随意调整次序
        // 配置有效性
        validators.add(planGeneConfigEffectiveValidator);
        // 生成类型有效性
        validators.add(planGeneTypeValidator);
        // 计划物料有效性
        validators.add(planGeneMaterialValidator);
    }

    @Override
    @SuppressWarnings({"rawtypes","unchecked"})
    public ValidateMessage doProcess(ValidateMessage originalMessage) {

        PlanGeneValidateResponse response = new PlanGeneValidateResponse();
        // 作为上下文传递
        originalMessage.setE(response);

        if (CollectionUtil.isNotEmpty(validators)) {
            for (IValidator validator : validators) {

                ValidateMessage validateMessage = validator.doValidate(originalMessage);

                // 合并校验响应模型
                response.merge((PlanGeneValidateResponse) validateMessage.getE());
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
