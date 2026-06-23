package com.inventory.middle.application.plan.demand.bo;

import lombok.Data;

import java.util.List;

/**
 * @author zhouxinzhong
 * @date 2021/10/13 17:15
 */
@Data
public class DemandPlanVersionSelectResBO {

    /**
     * 需求版本列表
     */
    private List<DemandPlanVersionBO> demandPlanVersionList;

}
