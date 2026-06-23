package com.inventory.middle.interfaces.web.plan.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 物料计划报表指标
 *
 * @author Danny.Lee
 * @date 2021/10/15
 */
@Data
@Schema(description = "物料计划报表指标")
public class MaterialPlanReportIndicatorVO implements Serializable {

    private static final long serialVersionUID = -8838803711143971483L;

    /**
     * 指标编码
     */
    @Schema(description = "指标编码")
    private String indicatorCode;

    /**
     * 指标名称
     */
    @Schema(description = "指标名称")
    private String indicatorName;

    /**
     * 指标数据
     */
    @Schema(description = "指标数据")
    private List<String> indicators;
}
