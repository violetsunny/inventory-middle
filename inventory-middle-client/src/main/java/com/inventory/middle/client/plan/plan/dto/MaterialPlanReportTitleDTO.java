package com.inventory.middle.client.plan.plan.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 物料计划报表标题
 *
 * @author Danny.Lee
 * @date 2021/10/2
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MaterialPlanReportTitleDTO implements Serializable {

    private static final long serialVersionUID = -9115499633306995178L;

    private String materialCode;

    private String materialDesc;

    private String planVersion;

    private LocalDate createDate;

    private String operator;

}
