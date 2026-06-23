package com.inventory.middle.application.plan.config.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author caosheng
 * @title: PlanParamEnumResBO
 * @projectName scm-plan-management
 * @description: 计划物料响应类
 * @date 2021/10/26 18:59
 */
@Data
public class PlanMaterialParamEnumResBO implements Serializable {

    private static final long serialVersionUID = 7816113035776700530L;

    private List<PlanParamBO> planMaterialParamPlanTypes;

    private List<PlanParamBO> demandStrategies;

    private List<PlanParamBO> demandTypes;

    private List<PlanParamBO> safetyStockCalculateTypes;

}
