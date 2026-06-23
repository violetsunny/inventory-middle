package com.inventory.middle.client.plan.plan.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

/**
 * 物料计划报表期初指标
 *
 * @author Danny.Lee
 * @date 2021/11/9
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MaterialPlanReportInitIndicatorDTO implements Serializable {

    private static final long serialVersionUID = -1839392147554621052L;

    /**
     * 期初指标
     */
    private Map<String, BigDecimal> initIndicators;
}
