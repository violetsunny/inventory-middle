package com.inventory.middle.interfaces.web.plan.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 物料计划报表时区
 *
 * @author Danny.Lee
 * @date 2021/10/15
 */
@Data
@Schema(description = "物料计划报表时段")
public class MaterialPlanReportPeriodVO implements Serializable {

    private static final long serialVersionUID = -7520010143694421538L;

    @Schema(description = "计划时段")
    private List<String> periodStartDates;
}
