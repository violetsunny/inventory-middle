/**
 * OYO.com Inc.
 * Copyright (c) 2017-2019 All Rights Reserved.
 */
package com.inventory.middle.client.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 物料库存类型
 *
 * @author dongguo.tao
 * @version 2021-09-26 15:22:23
 */
@AllArgsConstructor
@Getter
public enum InventorySupplyTypeEnum {

    IN_TRANSIT(1, "在途库存"),
    IN_STOCK(2, "在库库存"),
    ;

    private Integer code;
    private String desc;

    public static Boolean isExist(Integer code) {
        if (null == code){
            return Boolean.FALSE;
        }
        return Arrays.stream(InventorySupplyTypeEnum.values()).anyMatch(v -> v.getCode().equals(code));
    }
}
