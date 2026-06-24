package com.inventory.middle.client.plan.config.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author caosheng
 * @title: PlanParamEnumResDTO
 * @projectName scm-plan-management
 * @description: 计划参数枚举列表响应DTO
 * @date 2021/10/26 18:59
 */
@Data
public class PlanParamEnumListResDTO implements Serializable {

    private static final long serialVersionUID = -5995771850949806885L;

    /**
     * 计划执行类型-订货计算规则
     */
    private Map<PlanParamDTO, List<PlanParamDTO>> typeRules;
    /**
     * 订货计算规则-订货量类型
     */
    private Map<PlanParamDTO, List<PlanParamDTO>> ruleProduces;
}
