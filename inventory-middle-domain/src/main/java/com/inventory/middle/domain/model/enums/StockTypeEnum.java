/**
 * OYO.com Inc.
 * Copyright (c) 2017-2019 All Rights Reserved.
 */
package com.inventory.middle.domain.model.enums;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;
import top.kdla.framework.dto.IEnum;

/**
 * 物料操作类型
 *
 * @author kanglele
 * @version $Id: MaterialType, v 0.1 2019-11-05 17:56 Exp $
 */
@AllArgsConstructor
@Getter
public enum StockTypeEnum implements IEnum<Integer> {

    UNRESTRICTED(1, "良品"),
    DAMAGED(2, "残次品"),
    INSPECTION(3, "质检品"),
    ;

    private Integer code;
    private String desc;

    public static String valueByCode(Integer code) {
        if (code == null) {
            return "";
        }
        return Arrays.asList(values()).stream().filter(e -> code.equals(e.getCode())).map(StockTypeEnum::getDesc).findFirst().orElse("");
    }

    public static StockTypeEnum getByCode(Integer code) {
        if (code == null) {
            return UNRESTRICTED;
        }
        for (StockTypeEnum e : StockTypeEnum.values()) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        return null;
    }

    public static Boolean checkByCode(Integer code) {
        return Arrays.stream(StockTypeEnum.values()).anyMatch(v -> v.getCode().equals(code));
    }

}
