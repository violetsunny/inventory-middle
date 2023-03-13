package com.inventory.middle.client.enums;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Objects;

/**
 * 物料凭证参考的单据类型
 * @author dongguo
 */

public enum MaterialDocRefOrderTypeEnum {

    SALES_RETURN_ORDER(100,"销售退货订单"),
    LEND_IN_ORDER(102,"借出入库单"),
    PURCHASE_ORDER(103,"采购订单"),
    PRODUCTION_ORDER(104,"生产订单"),
    VC_PURCHASE_ORDER(105,"供应商寄售采购订单"),
    SC_PURCHASE_ORDER(106,"委外采购订单"),
    STOCK_TRANSFER_IN(107, "调拨入库单"),
    PICKING_BACK(108,"领料退库单"),
    GIVEAWAY_IN(109,"赠品入库单"),

    LEND_OUT_ORDER(200,"借出出库单"),
    PURCHASE_RETURN_ORDER(201,"采购退供订单"),
    SALES_ORDER(202,"销售订单"),
    PICKING_OUT(203,"领料出库单"),
    GIVEAWAY_BACK(204,"赠品退货单"),
    STOCK_TRANSFER_OUT(207, "调拨出库单"),

    MATERIAL_DOC(10, "物料凭证")
    ;

    @Getter
    private Integer code;
    @Getter
    private String desc;


    MaterialDocRefOrderTypeEnum(Integer code, String desc){
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
        return Arrays.asList(values()).stream().filter(e->code.equals(e.getCode())).map(MaterialDocRefOrderTypeEnum::getDesc).findFirst().orElse("");
    }
}
