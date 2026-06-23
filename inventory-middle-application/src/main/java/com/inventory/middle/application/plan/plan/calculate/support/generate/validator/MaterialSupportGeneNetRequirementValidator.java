package com.inventory.middle.application.plan.plan.calculate.support.generate.validator;

import com.inventory.middle.domain.plan.common.enums.ResponseCodeEnum;
import com.inventory.middle.domain.plan.common.rule.IValidator;
import com.inventory.middle.domain.plan.common.rule.ValidateMessage;
import com.inventory.middle.application.plan.plan.calculate.support.formula.Formula;
import com.inventory.middle.application.plan.plan.calculate.support.formula.Formulas;
import com.inventory.middle.application.plan.plan.calculate.support.formula.factor.FactorHelper;
import com.inventory.middle.application.plan.plan.calculate.support.formula.factor.MaterialFactor;
import com.inventory.middle.application.plan.plan.calculate.support.formula.indicator.Indicators;
import com.inventory.middle.application.plan.plan.calculate.support.generate.validator.model.MaterialSupportGeneValidateRequest;
import com.inventory.middle.application.plan.plan.calculate.support.generate.validator.model.MaterialSupportGeneValidateResponse;
import com.inventory.middle.application.plan.plan.config.bo.PlanBO;
import com.inventory.middle.application.plan.plan.config.bo.PlanMaterialParameterBO;
import com.inventory.middle.application.plan.plan.config.enums.OrderCalRuleEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;

import static com.inventory.middle.application.plan.plan.config.enums.CalculateParamsKeyEnum.ORDER_CAL_RULE;

/**
 * 物料计划准入-净需求规则
 *
 * @author Danny.Lee
 * @date 2021/11/2
 */
@Slf4j
@Component
public class MaterialSupportGeneNetRequirementValidator implements IValidator {

    @Resource
    private FactorHelper factorHelper;

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public ValidateMessage doValidate(ValidateMessage message) {

        ValidateMessage validateMessage = ValidateMessage.builder()
                .t(message.getT())
                .success(Boolean.TRUE)
                .build();

        final MaterialSupportGeneValidateRequest request =
                (MaterialSupportGeneValidateRequest) message.getT();
        final MaterialSupportGeneValidateResponse context =
                (MaterialSupportGeneValidateResponse) message.getE();

        final PlanBO plan = context.getPlan();
        final PlanMaterialParameterBO parameter = context.getParameter();

        final String orderCalRule = MapUtils.getString(plan.getPlanCalculateParams(), ORDER_CAL_RULE.getCode());

        if (OrderCalRuleEnum.isNetRequirement(orderCalRule)) {
            MaterialFactor factor = factorHelper.buildFactor(plan, parameter, request.getPlanDate());

            Formula formula = Formulas.formula(Indicators.NET_REQUIREMENT);

            // 净需求小于0-不支持物料生成物料计划
            if (formula.apply(factor).value().compareTo(BigDecimal.ZERO) <= 0) {
                return validateMessage
                        .setSuccess(Boolean.FALSE)
                        .setE(MaterialSupportGeneValidateResponse.of(ResponseCodeEnum.PI_ENTRANCE_NET_REQUIREMENT));
            }
        }

        return validateMessage;
    }


}
