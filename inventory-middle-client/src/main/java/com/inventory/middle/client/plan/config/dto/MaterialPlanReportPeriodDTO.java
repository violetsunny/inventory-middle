package com.inventory.middle.client.plan.config.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * 物料计划报表时段
 *
 * @author Danny.Lee
 * @date 2021/10/2
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MaterialPlanReportPeriodDTO implements Serializable {

    private static final long serialVersionUID = -3984735629976795379L;

    private List<LocalDate> periodStartDates;
}
