package com.inventory.middle.application.plan.plan.config.bo;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author caosheng
 * @title: PlanParamEnumResBO
 * @projectName scm-plan-management
 * @description: TODO
 * @date 2021/10/26 18:59
 */
@Data
public class PlanParamEnumResBO implements Serializable {

    private static final long serialVersionUID = 156027724511709248L;

    @Deprecated
    private ArrayList<PlanParamBO>   indefiniteQuantityStrategyList;
    @Deprecated
    private ArrayList<PlanParamBO>   orderStrategyList;
    @Deprecated
    private ArrayList<PlanParamBO>   planQuantityStrategyList;

    /**
     * 计划执行类型-订货计算规则
     */
    private Map<PlanParamBO, List<PlanParamBO>> typeRules;
    /**
     * 订货计算规则-订货量类型
     */
    private Map<PlanParamBO, List<PlanParamBO>> ruleProduces;

    public void appendTypeForRule(PlanParamBO type, PlanParamBO rule) {
        if (typeRules == null) {
            typeRules = Maps.newHashMap();
        }
        if (!typeRules.containsKey(type)) {
            typeRules.put(type, Lists.newArrayList());
        }
        typeRules.get(type).add(rule);
    }

    public void appendRuleForProduce(PlanParamBO rule,PlanParamBO produce){
        if (ruleProduces == null) {
            ruleProduces = Maps.newHashMap();
        }
        if (!ruleProduces.containsKey(rule)) {
            ruleProduces.put(rule, Lists.newArrayList());
        }
        ruleProduces.get(rule).add(produce);
    }
}
