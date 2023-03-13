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
 * @version $Id: MaterialDocCategoryEnum, v 0.1 2019-11-05 17:56 Exp $
 */
@AllArgsConstructor
@Getter
public enum MaterialDocCategoryEnum {

    IN(1, "入库"),
    OUT(2, "出库"),
    INOUT(3, "出入库"),
    CANCEL(4, "取消");

    private Integer code;
    private String desc;

    public static Boolean isExist(Integer code) {
        return Arrays.stream(MaterialDocCategoryEnum.values()).anyMatch(v -> v.getCode().equals(code));
    }
}
