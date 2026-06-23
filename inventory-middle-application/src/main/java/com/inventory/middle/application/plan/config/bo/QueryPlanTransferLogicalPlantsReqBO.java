package com.inventory.middle.application.plan.config.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author caosheng
 * @title: QueryPlanTransferlogicalPlantsReqBO
 * @projectName scm-plan-management
 * @description: BO
 * @date 2021/11/25 14:02
 */
@Data
public class QueryPlanTransferLogicalPlantsReqBO implements Serializable {

    private static final long serialVersionUID = -1009477897460837252L;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 	逻辑仓编码
     */
    private String logicalPlantNo;
}
