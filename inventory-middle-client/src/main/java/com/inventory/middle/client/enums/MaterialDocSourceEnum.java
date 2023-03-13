/**
 * OYO.com Inc.
 * Copyright (c) 2017-2019 All Rights Reserved.
 */
package com.inventory.middle.client.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 物料操作来源
 *
 * @author kanglele
 * @version $Id: MaterialDocSourceEnum, v 0.1 2019-11-05 17:56 Exp $
 */
@AllArgsConstructor
@Getter
public enum MaterialDocSourceEnum {

    IMP("IMP", "走IMP页面人工操作库存"),
    INTERFACE("INTERFACE", "走接口系统操作库存"),
    ;

    private String code;
    private String desc;

    public static Boolean isExist(String code) {
        return Arrays.stream(MaterialDocSourceEnum.values()).anyMatch(v -> v.getCode().equals(code));
    }
}
