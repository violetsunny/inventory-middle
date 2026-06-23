package com.inventory.middle.interfaces.web.plan.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 物料计划报表期初指标
 *
 * @author Danny.Lee
 * @date 2021/11/9
 */
@Data
@Schema(description = "物料计划报表期初指标")
public class MaterialPlanReportInitIndicatorVO implements Serializable {

    private static final long serialVersionUID = -7138502530168181110L;

    /**
     * 指标数据
     */
    @Schema(description = "期初指标数据")
    private Map<String, String> initIndicators;
}
