package com.inventory.middle.interfaces.web.plan.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 切换计划方案状态入参
 * @date 2021/10/7 19:10
 */
@Data
public class PlanChangeStatusReqDTO implements Serializable {

    private static final long serialVersionUID = -7542984373357103103L;

    @Schema(description = "计划方案id")
    private Long id;

    @Schema(description = "有效状态")
    private Integer status;
}