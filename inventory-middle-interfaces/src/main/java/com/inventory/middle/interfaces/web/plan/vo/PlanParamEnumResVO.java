package com.inventory.middle.interfaces.web.plan.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author caosheng
 * @title: PlanParamEnumResVO
 * @projectName scm-plan-bff
 * @description: 计划参数枚举值获取
 * @date 2021/10/26 19:28
 */
@Data
public class PlanParamEnumResVO implements Serializable {

    private static final long serialVersionUID = -1899213417485494882L;

    /**
     * 计划执行类型-订货计算规则
     */
    @Schema(description = "计划执行类型-订货计算规则")
    private Map<PlanParamVO, List<PlanParamVO>> typeRules;
    /**
     * 订货计算规则-订货量类型
     */
    @Schema(description = "订货计算规则-订货量类型")
    private Map<PlanParamVO, List<PlanParamVO>> ruleProduces;
}
