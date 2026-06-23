package com.inventory.middle.application.plan.demand.handler;

import com.inventory.middle.infra.plan.persistence.entity.DemandPlanPO;
import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author:Vincent.Xiao
 * @date:2021/10/14 13:54
 */
@Data
public class DemandPlanAmountRequestContext {

    /**
     * 对应的计划
     */
    DemandPlanPO demandPlanPO;

    /**
     * 具体的变化
     */
    List<DemandPlanMaterialAmount> changeAmountList;
}
