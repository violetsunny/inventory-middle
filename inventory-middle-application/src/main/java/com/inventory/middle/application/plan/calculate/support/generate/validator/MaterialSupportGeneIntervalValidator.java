package com.inventory.middle.application.plan.calculate.support.generate.validator;

import com.inventory.middle.domain.plan.common.enums.ResponseCodeEnum;
import com.inventory.middle.domain.plan.common.rule.IValidator;
import com.inventory.middle.domain.plan.common.rule.ValidateMessage;
import com.inventory.middle.infra.plan.util.DateUtils;
import com.inventory.middle.application.plan.calculate.support.generate.PlanGeneType;
import com.inventory.middle.application.plan.calculate.support.generate.validator.model.MaterialSupportGeneValidateRequest;
import com.inventory.middle.application.plan.calculate.support.generate.validator.model.MaterialSupportGeneValidateResponse;
import com.inventory.middle.application.plan.config.bo.PlanBO;
import com.inventory.middle.application.plan.config.enums.CalculateParamsKeyEnum;
import com.inventory.middle.infra.plan.persistence.dao.MaterialPlanInstanceDao;
import com.inventory.middle.infra.plan.persistence.entity.MaterialPlanInstancePO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.Optional;

/**
 * @author Danny.Lee
 * @date 2021/11/10
 */
@Slf4j
@Component
public class MaterialSupportGeneIntervalValidator implements IValidator {

    @Resource
    private MaterialPlanInstanceDao materialPlanInstanceDao;

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public ValidateMessage doValidate(ValidateMessage message) {

        ValidateMessage validateMessage = ValidateMessage.builder()
                .t(message.getT())
                .success(Boolean.TRUE)
                .build();

        MaterialSupportGeneValidateRequest request = (MaterialSupportGeneValidateRequest) message.getT();

        final String materialCode = request.getMaterialCode();
        final String plantCode = request.getLogicalPlantNo();
        final String tenant = request.getTenantId();
        MaterialPlanInstancePO materialPlanInstance = materialPlanInstanceDao.selectLatestByMaterial(
                materialCode, plantCode, tenant, PlanGeneType.MESSAGE.getCode());

        // 消息调度每小时至多产出一份内容
        if (null != materialPlanInstance) {
            MaterialSupportGeneValidateResponse response = (MaterialSupportGeneValidateResponse) message.getE();
            PlanBO plan = response.getPlan();
            // 默认1天
            Integer orderInterval = Optional.ofNullable(MapUtils.getInteger(plan.getPlanCalculateParams(),
                    CalculateParamsKeyEnum.ORDER_CAL_INTERVAL.getCode())).orElse(1);

            // 日期间隔
            if (LocalDate.now().isBefore(DateUtils.toLocalDate(materialPlanInstance.getCreateTime()).plusDays(orderInterval))) {
                return validateMessage
                        .setSuccess(Boolean.FALSE)
                        .setE(MaterialSupportGeneValidateResponse.of(ResponseCodeEnum.PI_ENTRANCE_MESSAGE_DURING_INTERVAL));
            }


        }
        return validateMessage;
    }


}
