package com.inventory.middle.application.plan.calculate.support.generate.validator.model;

import com.inventory.middle.domain.plan.common.enums.ResponseCodeEnum;
import com.inventory.middle.application.plan.config.bo.PlanBO;
import com.inventory.middle.application.plan.config.bo.PlanMaterialParameterBO;
import lombok.Getter;

/**
 * @author Danny.Lee
 * @date 2021/11/1
 */
@Getter
public class MaterialSupportGeneValidateResponse {

    private PlanBO plan;

    private PlanMaterialParameterBO parameter;

    private ResponseCodeEnum responseCode;

    public static MaterialSupportGeneValidateResponse of(ResponseCodeEnum responseCode) {
        MaterialSupportGeneValidateResponse response = new MaterialSupportGeneValidateResponse();
        response.responseCode = responseCode;
        return response;
    }

    public MaterialSupportGeneValidateResponse setPlan(PlanBO plan) {
        this.plan = plan;
        return this;
    }

    public MaterialSupportGeneValidateResponse setParameter(PlanMaterialParameterBO parameter) {
        this.parameter = parameter;
        return this;
    }

    public void merge(MaterialSupportGeneValidateResponse response) {
        if (null == response) {
            return;
        }
        if (null != response.getResponseCode()) {
            this.responseCode = response.getResponseCode();
        }
        if (null != response.getPlan()) {
            this.plan = response.getPlan();
        }
        if (null != response.getParameter()) {
            this.parameter = response.getParameter();
        }
    }
}
