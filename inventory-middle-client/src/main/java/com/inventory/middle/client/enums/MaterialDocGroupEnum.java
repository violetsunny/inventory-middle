package com.inventory.middle.client.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author kermit.liu on 2019/10/14
 */
@AllArgsConstructor
@Getter
public enum MaterialDocGroupEnum {
    QC0("QC", "期初切换相关", 0L, 9999999L),
    CG1("CG", "采购相关", 10000000L, 19999999L),
    LY2("LY", "领用相关", 20000000L, 29999999L),
    SC3("SC", "生产相关", 30000000L, 39999999L),
    DB4("DB", "调拨相关", 40000000L, 49999999L),
    ZY5("ZY", "转移相关", 50000000L, 59999999L),
    XS6("XS", "销售相关", 60000000L, 69999999L),
    TZ7("TZ", "涉及库存的出/入数量调整包括且不限于:差异/盘点/报废/赠品等", 70000000L, 79999999L),
    WL8("WL", "商品主数据变更带来的库存价值变更", 80000000L, 89999999L),
    FP9("FP", "发票校验", 90000000L, 99999999L),
    ;

    private String code;
    private String desc;
    private Long beginNo;
    private Long endNo;

    public static Boolean isExist(String code) {
        return Arrays.stream(MaterialDocGroupEnum.values()).anyMatch(v -> v.getCode().equals(code));
    }
}
