/**
 * OYO.com Inc.
 * Copyright (c) 2017-2019 All Rights Reserved.
 */
package com.inventory.middle.domain.model.enums;

import java.util.Arrays;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import top.kdla.framework.dto.IEnum;

/**
 * 物料凭证操作渠道 枚举
 *
 * @author dongguo.tao
 */
@AllArgsConstructor
@Getter
public enum MaterialDocChannelEnum implements IEnum<Integer> {

    INVENTORY(0, "库存中心"),
    SN(1, "数能"),
    ;

    private Integer code;
    private String desc;

    public static String valueByCode(Integer code) {
        if (code == null) {
            return "";
        }
        return Arrays.asList(values()).stream().filter(e -> code.equals(e.getCode())).map(MaterialDocChannelEnum::getDesc).findFirst().orElse("");
    }

    public static MaterialDocChannelEnum getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (MaterialDocChannelEnum e : MaterialDocChannelEnum.values()) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        return null;
    }

    public static boolean checkByCode(Integer code){
        return Objects.nonNull(getByCode(code));
    }

}
