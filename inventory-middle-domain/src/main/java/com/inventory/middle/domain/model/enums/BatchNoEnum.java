/**
 * kll Inc.
 * Copyright (c) 2021 All Rights Reserved.
 */
package com.inventory.middle.domain.model.enums;

import java.util.Objects;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;
import top.kdla.framework.dto.IEnum;

/**
 * 批次生成：枚举/单据+逻辑仓代码+时间
 *
 * @author kll
 * @version $Id: BatchNoEnum, v 0.1 2021/6/30 17:11 Exp $
 */
@AllArgsConstructor
@Getter
public enum BatchNoEnum implements IEnum<String> {
    QC("QC", "其它-期初", MaterialAdjustTypeEnum.OQC101),
    ZP("ZP", "其它-赠品", MaterialAdjustTypeEnum.OZP707),
    RXS("RXS", "销售退货入库", MaterialAdjustTypeEnum.XS621),
    CG("CG", "采购入库", MaterialAdjustTypeEnum.CG101),
    SC("SC", "生产订单", MaterialAdjustTypeEnum.SC301),
    RJC("RJC", "借出归还", MaterialAdjustTypeEnum.JC221),
    VJ("VJ", "供应商寄售采购入库", MaterialAdjustTypeEnum.VJ101),
    ODB_409("ODB", "调拨入（一步法）", MaterialAdjustTypeEnum.ODB409),
    ODB_411("ODB", "调拨入（一步法）", MaterialAdjustTypeEnum.ODB411),
    DB_403("DB", "调拨入（二步法）", MaterialAdjustTypeEnum.DB403),
    DB_407("DB", "调拨入（二步法）", MaterialAdjustTypeEnum.DB407),
    OWW_411("OWW", "自有库与SC委外库调拨", MaterialAdjustTypeEnum.OWW411),
    OWW_413("OWW", "SC委外库与自有库调拨", MaterialAdjustTypeEnum.OWW413),
    OKJ_411("OKJ", "自有库与CC客户寄售库调拨", MaterialAdjustTypeEnum.OKJ411),
    OKJ_413("OKJ", "CC客户寄售库与自有库调拨", MaterialAdjustTypeEnum.OKJ413),
    OGJ_411("OGJ", "自有库与VC供应商寄售库调拨", MaterialAdjustTypeEnum.OGJ411),
    OGJ_413("OGJ", "VC供应商寄售库与自有库调拨", MaterialAdjustTypeEnum.OGJ413),
    OZH("OZH", "不同物料间的转化（一步法）", MaterialAdjustTypeEnum.WL803),

    DEFAULT("", "", null);

    private String code;
    private String desc;
    private MaterialAdjustTypeEnum materialAdjustTypeEnum;

    public static String getBatchNoCode(MaterialAdjustTypeEnum materialAdjustTypeEnum) {
        if (Objects.isNull(materialAdjustTypeEnum)) {
            return BatchNoEnum.DEFAULT.getCode();
        }
        return Stream.of(BatchNoEnum.values()).filter(d -> d.getMaterialAdjustTypeEnum().equals(materialAdjustTypeEnum)).findFirst().orElse(BatchNoEnum.DEFAULT)
            .getCode();
    }
}
