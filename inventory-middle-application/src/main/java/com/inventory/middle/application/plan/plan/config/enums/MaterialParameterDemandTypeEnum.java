package com.inventory.middle.application.plan.plan.config.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * 物料计划参数-物料需求类型
 *
 * @author Danny.Lee
 * @date 2022/5/10
 */
@Getter
public enum MaterialParameterDemandTypeEnum {
    /**
     * 独立需求
     */
    CENTRALIZE(0, "集中");

    private final Integer code;

    private final String desc;

    MaterialParameterDemandTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static MaterialParameterDemandTypeEnum of(Integer code) {
        return Arrays.stream(MaterialParameterDemandTypeEnum.values())
                .filter(type -> Objects.equals(type.getCode(), code))
                .findFirst()
                .orElse(null);
    }

}
