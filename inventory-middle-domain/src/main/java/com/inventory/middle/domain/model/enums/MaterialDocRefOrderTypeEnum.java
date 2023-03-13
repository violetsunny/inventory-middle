package com.inventory.middle.domain.model.enums;

import java.util.Arrays;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import top.kdla.framework.dto.IEnum;

/**
 * 物料凭证参考的单据类型
 *
 * @author dongguo
 */

public enum MaterialDocRefOrderTypeEnum implements IEnum<Integer> {
    MATERIAL_DOC(10, "物料凭证"),
    SALES_RETURN_ORDER(100, "销售退货订单"),
    LEND_IN_ORDER(102, "借出入库单"),
    PURCHASE_ORDER(103, "采购订单"),
    PRODUCTION_ORDER(104, "生产订单"),
    VC_PURCHASE_ORDER(105, "供应商寄售采购订单"),
    SC_PURCHASE_ORDER(106, "委外采购订单"),
    LEND_OUT_ORDER(200, "借出出库单"),
    PURCHASE_RETURN_ORDER(201, "采购退供订单"),
    SALES_ORDER(202, "销售订单"),
    STOCK_TRANSFER_IN(107, "调拨入库单"),
    STOCK_TRANSFER_OUT(207, "调拨出库单"),
    ;

    @Getter
    private Integer code;
    @Getter
    private String desc;

    MaterialDocRefOrderTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static MaterialDocRefOrderTypeEnum getByCode(Integer code){
        if ( null == code){
            return null;
        }
        for (MaterialDocRefOrderTypeEnum value : values()) {
            if (value.getCode() == code.intValue()){
                return value;
            }
        }
        return null;
    }

    public static boolean checkByCode(Integer code){
        return Objects.nonNull(getByCode(code));
    }

    public static String getDescByCode(Integer code){
        MaterialDocRefOrderTypeEnum typeEnum = getByCode(code);
        return Objects.isNull(typeEnum) ? StringUtils.EMPTY : typeEnum.getDesc();
    }

    public static String valueByCode(Integer code) {
        if (code == null) {
            return "";
        }
        return Arrays.asList(values()).stream().filter(e -> code.equals(e.getCode())).map(MaterialDocRefOrderTypeEnum::getDesc).findFirst().orElse("");
    }

    public static Boolean checkByEmpty(Integer code) {
        //只有非空的时候才校验数值是否正确 || 空为true
        return Objects.isNull(code) || Arrays.stream(values()).anyMatch(v -> v.getCode().equals(code));
    }
}
