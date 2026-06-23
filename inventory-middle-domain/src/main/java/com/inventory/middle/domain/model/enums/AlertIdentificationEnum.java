/**
 * OYO.com Inc.
 * Copyright (c) 2017-2019 All Rights Reserved.
 */
package com.inventory.middle.domain.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 预警标识
 * @author dongugo.tao
 * @version
 */
@AllArgsConstructor
@Getter
public enum AlertIdentificationEnum {

    CEIL("已超过上限"),
    FLOOR("已低于下限"),
    ;

    private String desc;
}
