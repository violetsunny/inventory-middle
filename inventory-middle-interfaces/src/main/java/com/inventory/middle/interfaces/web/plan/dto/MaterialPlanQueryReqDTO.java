package com.inventory.middle.interfaces.web.plan.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 物料计划查询参数
 *
 * @author Danny.Lee
 * @date 2021/10/15
 */
@Data
@Schema(description = "物料计划查询参数")
public class MaterialPlanQueryReqDTO implements Serializable {

    private static final long serialVersionUID = 8256749652669724884L;
    /**
     * 计划方案号
     */
    @Schema(description = "计划方案号")
    private String planCode;

    /**
     * 计划版本号
     */
    @Schema(description = "计划版本号")
    private String planVersion;

    /**
     * 计算时间查询开始日期
     */
    @Schema(description = "计算时间查询开始日期")
    private Date calStartTimeStart;
    /**
     * 计算时间查询结束日期
     */
    @Schema(description = "计算时间查询结束日期")
    private Date calStartTimeEnd;
    /**
     * 计划类型
     */
    @Schema(description = "计划类型:0-手动/1-自动")
    private Integer planType;
    /**
     * 物料编码
     */
    @Schema(description = "物料编码")
    private String materialCode;
    /**
     * 逻辑仓编码
     */
    @Schema(description = "逻辑仓编码")
    private String logicalPlantNo;

    /**
     * 分页页码
     */
    @Schema(description = "页码:从1起始", required = true)
    private Integer pageNum;

    /**
     * 分页大小
     */
    @Schema(description = "分页大小", required = true)
    private Integer pageSize;


}
