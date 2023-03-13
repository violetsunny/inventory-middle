package com.inventory.middle.domain.model.enums;

import java.util.Objects;

import lombok.Getter;
import top.kdla.framework.dto.IEnum;

@Getter
public enum LogicalPlantTypeEnum implements IEnum<Integer> {

    NORMAL(0, "LP", "普通逻辑仓"),
    ;

    LogicalPlantTypeEnum(Integer code, String mark, String desc) {
        this.code = code;
        this.mark = mark;
        this.desc = desc;
    }

    private Integer code;
    private String mark;
    private String desc;

    public static LogicalPlantTypeEnum getByCode(int code) {
        for (LogicalPlantTypeEnum e : LogicalPlantTypeEnum.values()) {
            if (e.getCode() == code) {
                return e;
            }
        }
        return null;
    }

    public static String getMarkByCode(int code) {
        LogicalPlantTypeEnum logicalPlantTypeEnum = getByCode(code);
        return Objects.nonNull(logicalPlantTypeEnum) ? logicalPlantTypeEnum.getMark() : "";
    }
}
