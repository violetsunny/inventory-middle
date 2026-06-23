package com.inventory.middle.application.plan.plan.config.enums;

import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.inventory.middle.application.plan.plan.config.enums.OrderProduceTypeEnum.*;

/**
 * 订货计算规则
 *
 * @author Danny.Lee
 * @date 2022/5/7
 */
@Getter
public enum OrderCalRuleEnum {

    /**
     * 补货点规则
     */
    REPLENISH_POINT("OCR_0", "补货点规则") {
        @Override
        public List<OrderProduceTypeEnum> produceTypes() {
            return Lists.newArrayList(FIXED_BATCH, UNFIXED_GOAL_DIFFERENCE);
        }
    },

    /**
     * 净需求规则
     */
    NET_REQUIREMENT("OCR_1", "净需求规则") {
        @Override
        public List<OrderProduceTypeEnum> produceTypes() {
            return Lists.newArrayList(UNFIXED_NET_REQUIREMENT, UNFIXED_NET_REQUIREMENT_BATCH);
        }
    },

    /**
     * 定期补货规则
     */
    CYCLE_ORDER("OCR_2", "定期补货规则", Boolean.TRUE) {
        @Override
        public List<OrderProduceTypeEnum> produceTypes() {
            return Lists.newArrayList(FIXED_BATCH, UNFIXED_GOAL_DIFFERENCE);
        }
    },
    ;

    private final String code;
    private final String desc;
    private final Boolean useBookingPeriod;


    OrderCalRuleEnum(String code, String desc) {
        this(code, desc, Boolean.FALSE);
    }

    OrderCalRuleEnum(String code, String desc, Boolean useBookingPeriod) {
        this.code = code;
        this.desc = desc;
        this.useBookingPeriod = useBookingPeriod;
    }

    public abstract List<OrderProduceTypeEnum> produceTypes();

    public static OrderCalRuleEnum of(String code) {
        return Arrays.stream(OrderCalRuleEnum.values())
                .filter(orderCalRule -> Objects.equals(orderCalRule.getCode(), code))
                .findFirst()
                .orElse(null);

    }

    public static boolean isCycleTime(String code) {
        return CYCLE_ORDER.getCode().equalsIgnoreCase(code);
    }

    public static boolean isNetRequirement(String code) {
        return NET_REQUIREMENT.getCode().equalsIgnoreCase(code);
    }

    public static boolean isReplenishPoint(String code) {
        return REPLENISH_POINT.getCode().equalsIgnoreCase(code);
    }
}
