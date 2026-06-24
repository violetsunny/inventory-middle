package com.inventory.middle.application.plan.config.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author caosheng
 * @title: PlanTransferLogicalPlantsResBO
 * @projectName scm-plan-management
 * @description: 计划方案调拨逻辑仓响应BO
 * @date 2021/11/25 14:07
 */
@Data
public class PlanTransferLogicalPlantsResBO implements Serializable {

    private static final long serialVersionUID = -6413291247812665234L;

    private List<LogicalPlantsResBO> transferLogicalPlants;
}
