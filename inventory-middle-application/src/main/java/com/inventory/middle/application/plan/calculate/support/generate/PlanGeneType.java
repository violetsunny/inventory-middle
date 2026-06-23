package com.inventory.middle.application.plan.calculate.support.generate;

import lombok.Getter;

/**
 * 任务执行类型
 *
 * @author Danny.Lee
 * @date 2021/9/28
 */
@Getter
public enum PlanGeneType {
    /**
     * 手工
     */
    MANUAL(0, "手工"),

    /**
     * 任务
     */
    JOB(1, "任务"),

    /**
     * 消息
     */
    MESSAGE(2, "消息"),
    ;

    private final Integer code;
    private final String desc;

    PlanGeneType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
