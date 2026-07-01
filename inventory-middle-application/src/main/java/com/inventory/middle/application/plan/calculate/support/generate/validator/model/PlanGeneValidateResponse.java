package com.inventory.middle.application.plan.calculate.support.generate.validator.model;

import com.inventory.middle.domain.common.constants.ResponseCodeEnum;
import com.inventory.middle.domain.plan.common.bo.MaterialBO;
import com.inventory.middle.application.plan.config.bo.PlanBO;
import com.inventory.middle.application.plan.config.bo.PlanMaterialParameterBO;
import lombok.Getter;

import java.util.Map;

/**
 * 计划实例产出校验返回对象
 *
 * @author Danny.Lee
 * @date 2021/11/1
 */
@Getter
public class PlanGeneValidateResponse {

    private ResponseCodeEnum responseCode;

    private PlanBO plan;

    private Map<MaterialBO, PlanMaterialParameterBO> materialWithParameters;

    public static PlanGeneValidateResponse of(ResponseCodeEnum responseCode) {
        PlanGeneValidateResponse response = new PlanGeneValidateResponse();
        response.responseCode = responseCode;
        return response;
    }

    public PlanGeneValidateResponse setPlan(PlanBO plan) {
        this.plan = plan;
        return this;
    }

    public PlanGeneValidateResponse setMaterialWithParameters(Map<MaterialBO, PlanMaterialParameterBO> materialWithParameters) {
        this.materialWithParameters = materialWithParameters;
        return this;
    }

    public void merge(PlanGeneValidateResponse response) {
        if (null == response) {
            return;
        }
        if (null != response.getResponseCode()) {
            this.responseCode = response.getResponseCode();
        }
        if (null != response.getPlan()) {
            this.plan = response.getPlan();
        }
        if (null != response.getMaterialWithParameters()) {
            this.materialWithParameters = response.getMaterialWithParameters();
        }
    }
}
