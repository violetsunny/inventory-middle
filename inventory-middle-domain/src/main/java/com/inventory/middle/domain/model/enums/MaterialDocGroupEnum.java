package com.inventory.middle.domain.model.enums;

import java.util.Arrays;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import top.kdla.framework.dto.IEnum;

/**
 * 物料凭证组
 *
 * @author kll
 */
@AllArgsConstructor
@Getter
public enum MaterialDocGroupEnum implements IEnum<String> {
    QC0("QC", "期初切换相关", 0L, 9999999L),
    CG1("CG", "采购相关", 10000000L, 19999999L),
    LY2("LY", "领用相关", 20000000L, 29999999L),
    SC3("SC", "生产相关", 30000000L, 39999999L),
    DB4("DB", "调拨相关", 40000000L, 49999999L),
    ZY5("ZY", "转移相关", 50000000L, 59999999L),
    XS6("XS", "销售相关", 60000000L, 69999999L),
    TZ7("TZ", "库存数量调整", 70000000L, 79999999L),
    WL8("WL", "商品主数据变更带来的库存价值变更", 80000000L, 89999999L),
    FP9("FP", "发票校验", 90000000L, 99999999L),
    ;

    private String code;
    private String desc;
    private Long beginNo;
    private Long endNo;

    public static String valueByCode(String code) {
        if (code == null) {
            return "";
        }
        return Arrays.asList(values()).stream().filter(e -> code.equals(e.getCode())).map(MaterialDocGroupEnum::getDesc).findFirst().orElse("");
    }

    public static MaterialDocGroupEnum enumByCode(String code) {
        if (code == null) {
            return null;
        }
        return Arrays.asList(values()).stream().filter(e -> code.equals(e.getCode())).findFirst().orElse(null);
    }

    public static boolean isValidEnum(String code) {
        for (MaterialDocGroupEnum materialDocGroupEnum : MaterialDocGroupEnum.values()) {
            if (materialDocGroupEnum.getCode().equals(code)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkByCode(String code) {
        return Objects.nonNull(enumByCode(code));
    }
}
