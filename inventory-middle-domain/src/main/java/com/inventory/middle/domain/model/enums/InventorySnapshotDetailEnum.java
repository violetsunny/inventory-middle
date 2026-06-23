package com.inventory.middle.domain.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 库存详情管理维度
 */
@AllArgsConstructor
@Getter
public enum InventorySnapshotDetailEnum {

    LOGIC_PLANT(0,"逻辑仓维度"),
    LOCATION(1,"存储地点维度"),
    BATCH(2,"批次维度")
    ;

    private Integer code;
    private String desc;

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
