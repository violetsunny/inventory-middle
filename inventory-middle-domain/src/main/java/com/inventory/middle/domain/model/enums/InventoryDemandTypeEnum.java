package com.inventory.middle.domain.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 需求出库类型
 * demand类型 1-立即出库，2-计划出库
 *
 * @author vincent.li
 * @date 2021-10-20
 */
@AllArgsConstructor
@Getter
public enum InventoryDemandTypeEnum {

    /**
     * 立即出库
     */
    IMMEDIATE_OUTBOUND(1, "立即出库"),
    /**
     * 计划出库
     */
    PLANNED_OUTBOUND(2, "计划出库");

    /**
     * 编码
     */
    private Integer code;
    /**
     * 描述
     */
    private String desc;

    public static Boolean isExist(Integer code) {
        return Arrays.stream(InventoryDemandTypeEnum.values()).anyMatch(v -> v.getCode().equals(code));
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
