package com.inventory.middle.domain.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author dongguo.tao
 * @description
 * @date 2021-12-21 10:51:19
 */
@AllArgsConstructor
@NoArgsConstructor
public enum QrCodeTypeEnum {
    /**
     * 二维码
     */
    QR_CODE("二维码"),

    /**
     * 条形码
     */
    CODE_128("条形码");

    @Getter
    private String desc;
}
