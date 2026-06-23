package com.inventory.middle.application.plan.demand.bo;

import lombok.Data;

import java.util.List;

/**
 * @author zhouxinzhong
 * @date 2021/10/13 17:14
 */
@Data
public class DemandPlanVersionSelectReqBO {

    /**
     * 逻辑仓列表
     */
    private List<String> logicalPlantNos;

    /**
     * 租户id
     */
    private String tenantId;

}
