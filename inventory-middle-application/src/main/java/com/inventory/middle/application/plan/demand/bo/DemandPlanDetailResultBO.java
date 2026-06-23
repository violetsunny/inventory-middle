package com.inventory.middle.application.plan.demand.bo;

import lombok.Data;

import java.util.List;

/**
 * @author zhouxinzhong
 * @date 2021/10/10 10:25
 */
@Data
public class DemandPlanDetailResultBO {
    /**
     * 需求计划物料表id
     */
    private Long planMaterialId;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 物料描述
     */
    private String materialDesc;

    /**
     * 单位
     */
    private String materialUnit;

    /**
     * 逻辑仓编码
     */
    private String logicalPlantNo;

    /**
     * 物料需求列表
     */
    private List<PeriodDemandResultBO> demandList;
}
