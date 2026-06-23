package com.inventory.middle.client.plan.plan.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author caosheng
 * @title: QueryPlanTransferLogicalPlantReqDTO
 * @projectName scm-plan-management
 * @description: 查询物料计划参数可用调拨逻辑仓
 * @date 2021/11/25 11:22
 */
@Data
public class QueryPlanTransferLogicalPlantReqDTO implements Serializable {

    private static final long serialVersionUID = -7845794552808355836L;

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
