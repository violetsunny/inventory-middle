/**
 * OYO.com Inc.
 * Copyright (c) 2017-2019 All Rights Reserved.
 */
package com.inventory.middle.client.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 批次同步类型
 *
 * @author kanglele
 * @version $Id: BatchSysEnum, v 0.1 2019-11-05 17:56 Exp $
 */
@AllArgsConstructor
@Getter
public enum BatchSysEnum {

    UN_SYS(0, "不同步"),
    PRODUCT_SYS(1, "同步给产品中心"),
    INVENTORY_SYS(2, "同步给库存物料中心"),
    ;

    private Integer code;
    private String desc;

    public static BatchSysEnum getByCode(Integer code) {
        if (code == null) {
            return PRODUCT_SYS;
        }
        for (BatchSysEnum e : BatchSysEnum.values()) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        return null;
    }

    public static Boolean checkByCode(Integer code) {
        return Arrays.stream(BatchSysEnum.values()).anyMatch(v -> v.getCode().equals(code));
    }

}
