package com.inventory.middle.application.plan.plan.config.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PlanChangeStatusReqBO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Integer status;
    private String tenantId;
}
