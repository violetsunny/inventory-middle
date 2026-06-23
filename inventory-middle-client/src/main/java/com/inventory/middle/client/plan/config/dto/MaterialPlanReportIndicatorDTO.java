package com.inventory.middle.client.plan.config.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 物料计划报表指标
 *
 * @author Danny.Lee
 * @date 2021/10/2
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MaterialPlanReportIndicatorDTO implements Serializable {

    private static final long serialVersionUID = -1859493925847133072L;

    private String indicatorCode;

    private String indicatorName;

    private List<BigDecimal> indicators;
}
