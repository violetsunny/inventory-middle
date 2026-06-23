package com.inventory.middle.client.plan.plan.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author caosheng
 * @title: PlanTransferLogicalPlantsResDTO
 * @projectName scm-plan-management
 * @description: 返回逻辑仓信息
 * @date 2021/11/25 11:06
 */
@Data
public class PlanTransferLogicalPlantsResDTO implements Serializable {

    private static final long serialVersionUID = -7243007186232682470L;

        private List<LogicalPlantsResDTO> transferLogicalPlants;

}
