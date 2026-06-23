package com.inventory.middle.domain.plan.common.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * 计划实例状态
 *
 * @author Danny.Lee
 * @date 2021/10/25
 */
@Getter
public enum InstanceStatusEnum {

    /**
     * 未开始
     */
    READY(0, "未开始"),

    /**
     * 处理中
     */
    PROCESSING(1, "处理中"),

    /**
     * 运行失败
     */
    FAIL(2, "运行失败"),

    /**
     * 运行成功
     */
    SUCCESS(3, "运行成功"),
    ;

    private final Integer code;

    private final String desc;

    InstanceStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static InstanceStatusEnum of(Integer code) {
        return Arrays.stream(InstanceStatusEnum.values())
                .filter(status -> Objects.equals(status.getCode(), code))
                .findFirst()
                .orElse(null);
    }

    public static boolean isFail(Integer code) {
        return Objects.nonNull(code) && FAIL.getCode().equals(code);
    }

    public static boolean isCompleted(Integer code) {
        return Objects.nonNull(code) && (FAIL.getCode().equals(code) || SUCCESS.getCode().equals(code));
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
