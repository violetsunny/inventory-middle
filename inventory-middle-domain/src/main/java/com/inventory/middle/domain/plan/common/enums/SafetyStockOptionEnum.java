package com.inventory.middle.domain.plan.common.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * 安全库存计算类型
 *
 * @author Danny.Lee
 */
@Getter
public enum SafetyStockOptionEnum {

    UNSELECTED("0", "未选择"),
    FROM_FIXED_VALUE("1", "直接设定"),
    FROM_FORMULA("2", "公式计算"),
    ;

    SafetyStockOptionEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private final String code;
    private final String desc;

    public static SafetyStockOptionEnum of(String code) {
        return Arrays.stream(SafetyStockOptionEnum.values())
                .filter(option -> Objects.equals(option.getCode(), code))
                .findFirst()
                .orElse(null);
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
