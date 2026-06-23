package com.inventory.middle.client.plan.demand.dto;

import com.inventory.middle.client.plan.BaseDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * pl_demand_plan_material_period
 *
 * @date 2021-09-28
 */

@Data
public class DemandPlanMaterialBatchCreateReqDTO extends BaseDTO implements Serializable {
    private static final long serialVersionUID = -7804416561771997649L;
    /**
     * 主键
     */
    private Long demandPlanId;



    /**
     * 周期计划数量
     */
    private List<DemandPlanMaterialPeriodDTO> periodList;



}
