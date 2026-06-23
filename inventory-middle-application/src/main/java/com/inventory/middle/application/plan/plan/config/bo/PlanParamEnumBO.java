package com.inventory.middle.application.plan.plan.config.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class PlanParamEnumBO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Map<PlanTypeEnum, List<PlanRuleEnum>> typeRules;
    private Map<PlanRuleEnum, List<PlanProduceEnum>> ruleProduces;

    @Data
    public static class PlanTypeEnum implements Serializable {
        private Integer code;
        private String desc;
    }

    @Data
    public static class PlanRuleEnum implements Serializable {
        private Integer code;
        private String desc;
    }

    @Data
    public static class PlanProduceEnum implements Serializable {
        private Integer code;
        private String desc;
    }
}
