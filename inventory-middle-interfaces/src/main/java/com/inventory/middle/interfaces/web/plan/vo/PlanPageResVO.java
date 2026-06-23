package com.inventory.middle.interfaces.web.plan.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 分页查询计划方案 返参
 * @date 2021/10/7 16:03
 */
@Data
public class PlanPageResVO implements Serializable {

    private static final long serialVersionUID = -8959868070149734366L;

    @Schema(description = "计划方案号")
    private String planCode;

    @Schema(description = "计划方案描述")
    private String planDesc;

    @Schema(description = "计划执行类型")
    private Integer planType;

    @Schema(description = "计划执行类型描述")
    private String planTypeDesc;

    @Schema(description = "更新时间")
    private Date updateTime;

    @Schema(description = "更新时间 字符串类型")
    private String updateTimeStr;

    @Schema(description = "操作人")
    private String operatorName;

    @Schema(description = "状态")
    private Integer status;

    @Schema(description = "状态描述")
    private String statusDesc;

    @Schema(description = "计划id")
    private Long id;

    @Schema(description = "0-所有物料 1-指定物料")
    private Integer coverMaterialType;

    @Schema(description = "0-未导入 1-已导入")
    private Integer exported;

    @Schema(description = "0-不关联bom 1-关联bom")
    private Integer relatedBom;
}