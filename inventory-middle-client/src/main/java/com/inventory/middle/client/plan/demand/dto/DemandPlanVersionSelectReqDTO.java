package com.inventory.middle.client.plan.demand.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhouxinzhong
 * @date 2021/10/13 15:59
 * @description 查询逻辑仓下的需求计划信息
 */
@Data
public class DemandPlanVersionSelectReqDTO implements Serializable {

    private static final long serialVersionUID = 757601984115547817L;

    /**
     * 逻辑仓列表
     */
    private List<String> logicalPlantNos;

    /**
     * 租户id
     */
    private String tenantId;

}
