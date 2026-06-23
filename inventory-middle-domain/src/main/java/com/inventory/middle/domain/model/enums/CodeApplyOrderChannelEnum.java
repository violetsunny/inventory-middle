package com.inventory.middle.domain.model.enums;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * 建流转码申请单渠道 枚举
 *
 * @author hjs
 * @date 2021/12/22
 */
@Getter
public enum CodeApplyOrderChannelEnum {

    PC(1,"PC"),
    APP(2,"APP"),
    ;


    CodeApplyOrderChannelEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private Integer code;
    private String desc;

    public static CodeApplyOrderChannelEnum getByCode(Integer code) {
        if (Objects.isNull(code)){
            return null;
        }
        for (CodeApplyOrderChannelEnum e : CodeApplyOrderChannelEnum.values()) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        return null;
    }
    public static String getDescByCode(Integer code){
        CodeApplyOrderChannelEnum channelEnum = getByCode(code);
        return Objects.isNull(channelEnum) ? StringUtils.EMPTY : channelEnum.getDesc();
    }
    public static boolean checkByCode(Integer code) {
        return  Objects.nonNull(getByCode(code));
    }


    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
