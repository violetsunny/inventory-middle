package com.inventory.middle.interfaces.web.plan.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 物料计划报表标题
 *
 * @author Danny.Lee
 * @date 2021/10/15
 */
@Data
@Schema(description = "物料计划报表标题")
public class MaterialPlanReportTitleVO implements Serializable {

    private static final long serialVersionUID = 276483493619826013L;

    /**
     * 物料编码
     */
    @Schema(description = "物料编码")
    private String materialCode;
    /**
     * 物料名称
     */
    @Schema(description = "物料描述")
    private String materialDesc;
    /**
     * 计划版本号
     */
    @Schema(description = "物料计划版本号")
    private String planVersion;
    /**
     * 计划日期
     */
    @Schema(description = "计划日期")
    private String createDate;
    /**
     * 操作员
     */
    @Schema(description = "操作员")
    private String operator;
}
