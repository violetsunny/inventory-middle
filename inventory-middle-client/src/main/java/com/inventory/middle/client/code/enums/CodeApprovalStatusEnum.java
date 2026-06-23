package com.inventory.middle.client.code.enums;

/**
 * OYO.com Inc.
 * Copyright (c) 2017-2019 All Rights Reserved.
 */

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 码审批状态枚举
 * @author dongguo.tao
 * @date 2021-12-16 15:48:00
 */
@AllArgsConstructor
@Getter
public enum CodeApprovalStatusEnum {

    UN_APPLY(0,"未申请"),
    UNAPPROVED(1,"未审批"),
    PASSED(2,"已通过"),
    REFUSED(3,"已拒绝"),
    ;

    private int code;
    private String desc;

    public static CodeApprovalStatusEnum getByCode(Integer code){
        if (Objects.isNull(code)){
            return null;
        }
        for (CodeApprovalStatusEnum value : CodeApprovalStatusEnum.values()) {
            if (value.code == code){
                return value;
            }
        }
        return null;
    }
    public static String getDescByCode(Integer code){
        CodeApprovalStatusEnum statusEnum = getByCode(code);
        return Objects.isNull(statusEnum) ? null : statusEnum.getDesc();
    }

    public static boolean checkByCode(Integer code){
        return Objects.nonNull(getByCode(code));
    }

    public static boolean checkApprovalStatus(Integer code){
        CodeApprovalStatusEnum statusEnum = getByCode(code);
        if (null == statusEnum){
            return false;
        }
        return code.equals(CodeApprovalStatusEnum.PASSED.getCode())
                || code.equals(CodeApprovalStatusEnum.REFUSED.getCode());
    }
}
