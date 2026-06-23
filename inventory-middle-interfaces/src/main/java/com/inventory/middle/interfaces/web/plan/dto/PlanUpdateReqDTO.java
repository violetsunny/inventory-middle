package com.inventory.middle.interfaces.web.plan.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 更新计划方案 入参
 * @date 2021/10/7 8:34
 */
@Data
public class PlanUpdateReqDTO extends PlanCreateReqDTO implements Serializable {

    private static final long serialVersionUID = -1129937987438035913L;

    @Schema(description = "计划方案Id" , required = true)
    private Long id;
}