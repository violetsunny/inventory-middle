/**
 * OYO.com Inc.
 * Copyright (c) 2017-2019 All Rights Reserved.
 */
package com.inventory.middle.domain.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import top.kdla.framework.dto.IEnum;

/**
 * 物料操作类型
 * @author kanglele
 * @version $Id: MaterialType, v 0.1 2019-11-05 17:56 Exp $
 */
@AllArgsConstructor
@Getter
public enum QueryBatchNoTypeEnum implements IEnum<Integer> {

    SUPPLY(1,"收货方"),
    DEMAND(2,"发货方"),
    ;

    private Integer code;
    private String desc;
}
