/**
 * OYO.com Inc.
 * Copyright (c) 2017-2019 All Rights Reserved.
 */
package com.inventory.middle.client.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 物料操作类型
 *
 * @author kanglele
 * @version $Id: MaterialType, v 0.1 2019-11-05 17:56 Exp $
 */
@AllArgsConstructor
@Getter
public enum StockTypeEnum {

    UNRESTRICTED(1, "良品"),
    DAMAGED(2, "残次品"),
    INSPECTION(3, "质检品"),
    ;

    private Integer code;
    private String desc;

    public static Boolean isExist(Integer code) {
        if (null == code){
            return Boolean.FALSE;
        }
        return Arrays.stream(StockTypeEnum.values()).anyMatch(v -> v.getCode().equals(code));
    }
}
