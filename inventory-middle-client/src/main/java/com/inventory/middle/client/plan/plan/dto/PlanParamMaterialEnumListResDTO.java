package com.inventory.middle.client.plan.plan.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author caosheng
 * @title: PlanParamEnumResDTO
 * @projectName scm-plan-management
 * @description: 计划物料参数响应类
 * @date 2021/10/26 18:59
 */
@Data
public class PlanParamMaterialEnumListResDTO implements Serializable {

    private static final long serialVersionUID = 156027724511709248L;

    private ArrayList<PlanParamDTO> planMaterialParamPlanTypeList;

    private ArrayList<PlanParamDTO> demandStrategyList;

    private ArrayList<PlanParamDTO> demandTypeList;

    private ArrayList<PlanParamDTO> safetyStockCalculateTypeList;

}
