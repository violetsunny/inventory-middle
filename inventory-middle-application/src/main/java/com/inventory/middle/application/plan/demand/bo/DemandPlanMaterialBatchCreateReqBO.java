package com.inventory.middle.application.plan.demand.bo;

import com.inventory.middle.domain.plan.common.bo.BaseBo;
import lombok.Data;

import java.util.List;

/**
 * pl_demand_plan_material_period
 *
 * @date 2021-09-28
 */

@Data
public class DemandPlanMaterialBatchCreateReqBO extends BaseBo {
    private static final long serialVersionUID = -7804416561771997649L;
    /**
     * 主键
     */
    private Long demandPlanId;


    /**
     * 周期计划数量
     */
    private List<DemandPlanMaterialPeriodBO> periodList;


    @Override
    public String toLog() {
        return this.toString();
    }
}