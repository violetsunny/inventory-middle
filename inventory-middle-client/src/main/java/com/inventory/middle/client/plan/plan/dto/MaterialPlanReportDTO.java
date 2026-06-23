package com.inventory.middle.client.plan.plan.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 物料计划报表
 *
 * @author Danny.Lee
 * @date 2021/9/29
 */
@Data
public class MaterialPlanReportDTO implements Serializable {

    private static final long serialVersionUID = 7741900405405357858L;

    /**
     * 标题
     */
    private MaterialPlanReportTitleDTO title;

    /**
     * 参数
     */
    private MaterialPlanReportParamDTO param;

    /**
     * 时段
     */
    private MaterialPlanReportPeriodDTO period;

    /**
     * 期初指标
     */
    private MaterialPlanReportInitIndicatorDTO initIndicator;

    /**
     * 指标
     */
    private List<MaterialPlanReportIndicatorDTO> indicators;

}
