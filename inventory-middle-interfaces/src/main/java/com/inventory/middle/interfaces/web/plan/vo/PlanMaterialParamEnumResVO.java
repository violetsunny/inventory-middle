package com.inventory.middle.interfaces.web.plan.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author caosheng
 * @title: PlanParamEnumResVO
 * @projectName scm-plan-bff
 * @description: 计划参数枚举值获取
 * @date 2021/10/26 19:28
 */
@Data
public class PlanMaterialParamEnumResVO implements Serializable {

    private static final long serialVersionUID = -1899213417485494882L;

    @Schema(description = "计划类型")
    ArrayList<PlanParamVO> planMaterialParamPlanTypeEnum;

    @Schema(description = "需求响应")
    ArrayList<PlanParamVO> demandStrategyEnum;

    @Schema(description = "物料需求类型")
    ArrayList<PlanParamVO> demandTypeEnum;

    @Schema(description = "安全库存计算类型")
    ArrayList<PlanParamVO> safetyStockCalculateTypeEnum;


}
