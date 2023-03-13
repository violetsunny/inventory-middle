/**
 * kll Inc.
 * Copyright (c) 2021 All Rights Reserved.
 */
package com.inventory.middle.domain.model.enums;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;
import top.kdla.framework.dto.IEnum;

/**
 * @author kll
 * @version $Id: MapStatusEnum, v 0.1 2021/6/23 21:38 Exp $
 */
@AllArgsConstructor
@Getter
public enum MapStatusEnum implements IEnum<Integer> {
    INIT(10,"初始化"),
    NO_CAL(20,"无需计算"),
    SUCCESS(30,"计算成功"),
    FAIL(40,"计算失败"),
    ;

    private Integer code;
    private String desc;

    public static String valueByCode(Integer code) {
        if (code == null) {
            return "";
        }
        return Arrays.asList(values()).stream().filter(e->code.equals(e.getCode())).map(MapStatusEnum::getDesc).findFirst().orElse("");
    }
}
