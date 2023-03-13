package com.inventory.middle.domain.model.enums;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import top.kdla.framework.dto.IEnum;

/**
 * 币种枚举类
 */
@Getter
public enum CurrencyEnum implements IEnum<String> {
    CNY("CNY", 2, "人民币"),
    USD("USD", 2, "美元"),
    HKD("HKD", 2, "港币"),
    JPY("JPY", 0, "日元"),
    NZD("NZD", 2, "新西兰元"),
    AUD("AUD", 2, "澳大利亚元"),
    MOP("MOP", 2, "澳门元"),
    CAD("CAD", 2, "加拿大元"),
    KRW("KRW", 0, "韩元"),
    AED("AED", 2, "迪拉姆"),
    SGD("SGD", 2, "新加坡元"),
    EUR("EUR", 2, "欧元"),
    GBP("GBP", 2, "英镑"),
    ;
    private String code;

    private Integer digits;

    private String desc;

    CurrencyEnum(String code, Integer digits, String description) {
        this.code = code;
        this.digits = digits;
        this.desc = description;
    }

    public static CurrencyEnum getByCode(String code) {
        for (CurrencyEnum e : CurrencyEnum.values()) {
            if (e.code.equalsIgnoreCase(code)) {
                return e;
            }
        }
        return null;
    }

    public static Boolean checkByCode(String code) {
        if(StringUtils.isBlank(code)){
            return Boolean.FALSE;
        }
        return Arrays.stream(CurrencyEnum.values()).anyMatch(v -> StringUtils.equals(v.getCode(),code));
    }
}
