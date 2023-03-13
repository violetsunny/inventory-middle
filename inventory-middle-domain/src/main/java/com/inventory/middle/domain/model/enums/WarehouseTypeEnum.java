package com.inventory.middle.domain.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import top.kdla.framework.dto.IEnum;

/**
 * 物理仓类型枚举
 *
 * @author vincent.li
 * @date 2021/10/21
 */
@Getter
@AllArgsConstructor
public enum WarehouseTypeEnum implements IEnum<Integer> {

    /**
     * 真实物理仓
     */
    REAL(1,"真实物理仓"),
    /**
     * 虚拟物理仓
     */
    VIRTUAL(0, "虚拟物理仓");
    /**
     * 编码
     */
    private Integer code;
    /**
     * 描述
     */
    private String desc;
}
