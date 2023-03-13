/**
 * OYO.com Inc.
 * Copyright (c) 2017-2019 All Rights Reserved.
 */
package com.inventory.middle.client.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 物料凭证类型
 *
 * @author kanglele
 * @version $Id: MaterialDocTypeEnum, v 0.1 2019-11-26 21:28 oyo Exp $
 */
@AllArgsConstructor
@Getter
public enum MaterialDocTypeEnum {

    INVENTORY_MANAGEMENT(1, "库存管理类"),
    PERMANENT_ASSETS(2, "固定资产类"),
    SERVICE_TYPE(3, "服务类"),
    PROJECT_TYPE(4, "项目类"),
    RECEIPT_TYPE(5, "发票类"),
    ;

    private Integer code;
    private String desc;

    public static Boolean isExist(Integer code) {
        return Arrays.stream(MaterialDocTypeEnum.values()).anyMatch(v -> v.getCode().equals(code));
    }
}
