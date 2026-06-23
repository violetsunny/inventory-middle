package com.inventory.middle.application.plan.plan.config.enums;

import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 计划执行类型
 *
 * @author Danny.Lee
 * @date 2022/5/7
 */
@Getter
public enum PlanExecuteTypeEnum {

    /**
     * 手动
     */
    MANUAL(0, "手动"){
        @Override
        public List<OrderCalRuleEnum> rules() {
            return Lists.newArrayList(OrderCalRuleEnum.NET_REQUIREMENT);
        }
    },
    /**
     * 自动
     */
    AUTOMATIC(1, "自动"){
        @Override
        public List<OrderCalRuleEnum> rules() {
            return Lists.newArrayList(OrderCalRuleEnum.values());
        }
    },
    ;

    PlanExecuteTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private final Integer code;

    private final String desc;


    public static PlanExecuteTypeEnum of(Integer code) {
        return Arrays.stream(PlanExecuteTypeEnum.values())
                .filter(executeType -> Objects.equals(executeType.getCode(), code))
                .findFirst().orElse(null);
    }

    public abstract List<OrderCalRuleEnum> rules();
}
