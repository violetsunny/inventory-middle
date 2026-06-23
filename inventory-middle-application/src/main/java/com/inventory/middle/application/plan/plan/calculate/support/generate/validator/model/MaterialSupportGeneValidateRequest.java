package com.inventory.middle.application.plan.plan.calculate.support.generate.validator.model;

import lombok.Data;

import java.time.LocalDate;

/**
 * @author Danny.Lee
 * @date 2021/11/2
 */
@Data
public class MaterialSupportGeneValidateRequest {

    private String materialCode;

    private String logicalPlantNo;

    private String tenantId;

    private LocalDate planDate;
}
