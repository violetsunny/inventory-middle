package com.inventory.middle.client.plan.config.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author caosheng
 * @title: LogicalPlantsResDTO
 * @projectName scm-plan-management
 * @description: 逻辑仓信息
 * @date 2021/11/25 11:15
 */
@Data
public class LogicalPlantsResDTO implements Serializable {

    private static final long serialVersionUID = 4928158490312304183L;

    /**
     * 	逻辑仓编码
     */
    private String logicalPlantNo;

    /**
     * 逻辑仓名称
     */
    private String logicalPlantName;

}
