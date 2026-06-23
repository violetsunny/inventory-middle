package com.inventory.middle.domain.plan.common.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * 物料需求类型
 *
 * @author Danny.Lee
 */
@Getter
public enum MaterialDemandTypeEnum {

    CENTRALIZE(0, "集中"),
    ;

    private final Integer code;
    private final String desc;

    MaterialDemandTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static MaterialDemandTypeEnum of(Integer code) {
        return Arrays.stream(MaterialDemandTypeEnum.values())
                .filter(type -> Objects.equals(type.getCode(), code))
                .findFirst()
                .orElse(null);
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
