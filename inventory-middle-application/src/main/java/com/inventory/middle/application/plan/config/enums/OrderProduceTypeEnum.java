package com.inventory.middle.application.plan.config.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * 订货量策略
 *
 * @author Danny.Lee
 * @date 2022/5/7
 */
@Getter
public enum OrderProduceTypeEnum {

    /**
     * 定量
     */
    FIXED_BATCH("OPT_0", "定量"),

    /**
     * 不定量-根据净需求
     */
    UNFIXED_NET_REQUIREMENT("OPT_1", "不定量-根据净需求"),

    /**
     * 不定量-根据净需求+订货批量
     */
    UNFIXED_NET_REQUIREMENT_BATCH("OPT_2", "不定量-根据净需求+订货批量"),

    /**
     * 不定量-根据目标差额
     */
    UNFIXED_GOAL_DIFFERENCE("OPT_3", "不定量-根据目标差额"),
    ;

    private final String code;
    private final String desc;

    OrderProduceTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static OrderProduceTypeEnum of(String code) {
        return Arrays.stream(OrderProduceTypeEnum.values())
                .filter(opt -> Objects.equals(opt.getCode(), code))
                .findFirst()
                .orElse(null);
    }
}
