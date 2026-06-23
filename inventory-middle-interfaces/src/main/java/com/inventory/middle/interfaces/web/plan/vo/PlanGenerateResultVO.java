package com.inventory.middle.interfaces.web.plan.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 计划产出结果
 *
 * @author Danny.Lee
 * @date 2021/10/18
 */
@Data
@Schema(description = "计划方案执行结果")
public class PlanGenerateResultVO implements Serializable {

    private static final long serialVersionUID = 4119569111815242477L;

    /**
     * 计划版本号
     */
    @Schema(description = "计划版本号")
    private Long id;

    /**
     * 计划方案ID
     */
    @Schema(description = "计划方案ID")
    private Long planId;

    /**
     * 计划方案编码
     */
    @Schema(description = "计划方案编码")
    private String planCode;

    /**
     * 计划方案执行状态
     */
    @Schema(description = "计划执行状态")
    private String status;
}
