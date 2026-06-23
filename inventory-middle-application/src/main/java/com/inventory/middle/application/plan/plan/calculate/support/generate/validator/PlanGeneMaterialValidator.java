package com.inventory.middle.application.plan.plan.calculate.support.generate.validator;

import com.inventory.middle.domain.plan.common.enums.ResponseCodeEnum;
import com.inventory.middle.domain.plan.common.bo.PlanBaseBO;
import com.inventory.middle.domain.plan.common.bo.MaterialBO;
import com.inventory.middle.domain.plan.common.rule.IValidator;
import com.inventory.middle.domain.plan.common.rule.ValidateMessage;
import com.inventory.middle.application.plan.plan.calculate.bo.PlanGenerateRequestBO;
import com.inventory.middle.application.plan.plan.config.bo.PlanBO;
import com.inventory.middle.application.plan.plan.config.bo.PlanMaterialParamQueryReqBO;
import com.inventory.middle.application.plan.plan.config.bo.PlanMaterialParameterBO;
import com.inventory.middle.application.plan.plan.config.service.PlanConfigService;
import com.inventory.middle.application.plan.plan.calculate.support.generate.validator.model.PlanGeneValidateResponse;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 计划方案覆盖范围有效性
 *
 * @author Danny.Lee
 * @date 2021/11/1
 */
@Slf4j
@Component
public class PlanGeneMaterialValidator implements IValidator {

    @Resource
    private PlanConfigService planConfigService;

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public ValidateMessage doValidate(ValidateMessage message) {
        ValidateMessage validateMessage = ValidateMessage.builder()
                .t(message.getT())
                .success(Boolean.TRUE)
                .build();

        final PlanGenerateRequestBO request = (PlanGenerateRequestBO) message.getT();
        final PlanGeneValidateResponse context = (PlanGeneValidateResponse) message.getE();
        final PlanBO plan = context.getPlan();

        Map<MaterialBO, PlanMaterialParameterBO> materialsWithParameter = this.findPlanMaterialsWithParameter(plan, request);

        if (MapUtils.isEmpty(materialsWithParameter)) {
            return validateMessage
                    .setSuccess(Boolean.FALSE)
                    .setE(PlanGeneValidateResponse.of(ResponseCodeEnum.PI_PARAMS_EMPTY_MATERIALS));
        }

        // 上下文填充计划配置计划物料范围
        return validateMessage.setE(new PlanGeneValidateResponse().setMaterialWithParameters(materialsWithParameter));
    }

    private Map<MaterialBO, PlanMaterialParameterBO> findPlanMaterialsWithParameter(PlanBO plan, PlanGenerateRequestBO request) {
        Map<MaterialBO, PlanMaterialParameterBO> materialsWithParameter = Maps.newHashMap();
        List<MaterialBO> materials;
        if (CollectionUtils.isNotEmpty(request.getCoverMaterials())) {
            materials = request.getCoverMaterials();
        } else {
            materials = planConfigService.queryMaterialsByPlan(plan);
        }
        // todo 批处理以提高性能
        for (MaterialBO material : materials) {
            Optional.ofNullable(this.findMaterialParameter(material)).ifPresent(parameter -> {
                materialsWithParameter.put(material, parameter);
            });
        }
        return materialsWithParameter;
    }

    private PlanMaterialParameterBO findMaterialParameter(MaterialBO material) {
        PlanMaterialParamQueryReqBO request = new PlanMaterialParamQueryReqBO();
        request.setTenantId(material.getTenantId());
        request.setMaterialCode(material.getMaterialCode());
        request.setLogicalPlantNo(material.getLogicalPlantNo());
        return planConfigService.queryByMaterialCodeAndLogicalPlantNo(request);
    }
}
