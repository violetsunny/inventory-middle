package com.inventory.middle.domain.model.enums;

import java.util.Objects;

import lombok.Getter;
import top.kdla.framework.dto.IEnum;

/**
 * @author dongguo
 */

@Getter
public enum StorageLocationTypeEnum implements IEnum<Integer> {

    NORMAL(0, "SL","普通存储地点"),
    SPECIAL_CONSIGNMENT(1, "CC","客户寄售库"),
    SPECIAL_RECOVERY(2, "PC","待回收包装库"),
    SPECIAL_CONSIGN_MANUFACTURE(3, "SC","委外加工物资库"),
    SPECIAL_CONSIGNMENT_SUPPLIER(4, "VC","供应商寄售库"),
    SPECIAL_BACK(5, "PV","待退回包装库"),
    SPECIAL_SELL(6, "SS","销售订单库"),
    ;

    StorageLocationTypeEnum(Integer code, String mark, String desc) {
        this.code = code;
        this.mark = mark;
        this.desc = desc;
    }

    private Integer code;
    private String mark;
    private String desc;

    public static StorageLocationTypeEnum getByCode(Integer code) {
        for (StorageLocationTypeEnum e : StorageLocationTypeEnum.values()) {
            if (Objects.equals(e.getCode(),code)) {
                return e;
            }
        }
        return null;
    }

    public static boolean checkByCode(Integer code){
        return Objects.nonNull(getByCode(code));
    }

}
