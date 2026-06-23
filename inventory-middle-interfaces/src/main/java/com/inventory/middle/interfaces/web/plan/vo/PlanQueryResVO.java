package com.inventory.middle.interfaces.web.plan.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 计划查询返参
 * @date 2021/10/7 8:04
 */
@Data
public class PlanQueryResVO implements Serializable {

    private static final long serialVersionUID = 7277417454745022387L;

    @Schema(description = "方案Id")
    private Long id;

    @Schema(description = "方案编码")
    private String planCode;

    @Schema(description = "覆盖物料类型 0-所有物料 1-指定物料")
    private Integer coverMaterialType;

    @Schema(description = "方案描述")
    private String planDesc;

    @Schema(description = "状态描述")
    private Integer planType;

    @Schema(description = "计划展望期(天)")
    private Integer planHorizon;

    @Schema(description = "计划开始时间")
    private Date planStartTime;

    @Schema(description = "计划开始时间 字符串类型")
    private String planStartTimeStr;

    @Schema(description = "状态（0-已失效/1-已生效）")
    private Integer status;

    @Schema(description = "创建人")
    private String operatorId;

    @Schema(description = "更新时间（默认当前时间）")
    private Date updateTime;

    @Schema(description = "更新时间 字符串类型")
    private String updateTimeStr;

    @Schema(description = "租户id")
    private String tenantId;

    @Schema(description = "需求文件 key->id value->name）")
    private Map<Long, String> demandPlanFile;

    @Schema(description = "覆盖逻辑仓 key->no value->name")
    private Map<String, String> coverLogicalPlant;

    @Schema(description = "投放参数（JSON结构）")
    private Map<String, Object> planDeliveryParams;

    @Schema(description = "计算参数（JSON结构）")
    private Map<String, Object> planCalculateParams;

    /**
     * 是否关联bom 0不关联 1关联
     */
    @Schema(description = "是否关联bom")
    private Integer relatedBom;

}