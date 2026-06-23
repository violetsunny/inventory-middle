package com.inventory.middle.application.plan.demand.bo;

import lombok.Data;

/**
 * @author zhouxinzhong
 * @date 2021/10/13 17:17
 */
@Data
public class DemandPlanVersionBO {

    /**
     * 需求计划id
     */
    Long demandPlanId;

    /**
     * 需求计划版本号
     */
    String planVersion;

    /**
     * 逻辑仓编码
     */
    private String logicalPlantNo;

}
