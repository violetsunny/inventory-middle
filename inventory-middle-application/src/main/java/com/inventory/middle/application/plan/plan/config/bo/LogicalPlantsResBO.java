package com.inventory.middle.application.plan.plan.config.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author caosheng
 * @title: LogicalPlantsResBO
 * @projectName scm-plan-management
 * @description: 查询计划物料参数响应bo
 * @date 2021/11/25 14:08
 */
@Data
public class LogicalPlantsResBO implements Serializable {

    private static final long serialVersionUID = 7915160291357472045L;

    /**
     * 	逻辑仓编码
     */
    private String logicalPlantNo;

    /**
     * 逻辑仓名称
     */
    private String logicalPlantName;
}
