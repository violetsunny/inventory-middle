package com.inventory.middle.domain.model.enums;

import java.util.Arrays;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import top.kdla.framework.dto.IEnum;

/**
 * @author holmes
 * @Classname MaterialDocRefTypeEnum
 * @Description 物料凭证-移动类型-是否允许参考业务单据号
 * @Date 2021/10/21 13:52
 */
@AllArgsConstructor
@Getter
public enum MaterialDocRefTypeEnum implements IEnum<Integer> {
    NO(0, "否"),
    YES(1, "是"),
    ALL(2, "可选");

    private Integer code;
    private String desc;

    public static String valueByCode(Integer code) {
        if (null == code) {
            return "";
        }
        return Arrays.asList(values()).stream().filter(e -> code.equals(e.getCode())).map(MaterialDocRefTypeEnum::getDesc).findFirst().orElse("");
    }

    public static boolean checkByCode(Integer code) {
        return StringUtils.isNotBlank(valueByCode(code));
    }

    public static MaterialDocRefTypeEnum tansfer(Integer code) {
        if (Objects.isNull(code)) {
            return null;
        }
        return Arrays.asList(values()).stream().filter(e -> code.equals(e.getCode())).findFirst().orElse(null);
    }
}
