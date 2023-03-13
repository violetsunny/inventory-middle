package com.inventory.middle.client.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 物料凭证标签枚举
 * <p>
 */
@AllArgsConstructor
@Getter
public enum MaterialAdjustTypeEnum {

    OQC101(MaterialDocCategoryEnum.IN, MaterialDocGroupEnum.QC0, "OQC101" , "期初库存同步" , "+"),
    OQC102(MaterialDocCategoryEnum.CANCEL, MaterialDocGroupEnum.QC0, "OQC102" , "期初库存同步-撤销" , "-" , MaterialDocRefOrderTypeEnum.MATERIAL_DOC),

    CG101(MaterialDocCategoryEnum.IN, MaterialDocGroupEnum.CG1, "CG101" , "采购入库" , "+" , MaterialDocRefOrderTypeEnum.PURCHASE_ORDER),
    CG102(MaterialDocCategoryEnum.CANCEL, MaterialDocGroupEnum.CG1, "CG102" , "采购入库-冲销（仓库操作失误）" , "-" , MaterialDocRefOrderTypeEnum.MATERIAL_DOC),
    CG121(MaterialDocCategoryEnum.OUT, MaterialDocGroupEnum.CG1, "CG121" , "采购退供出库" , "-" , MaterialDocRefOrderTypeEnum.PURCHASE_RETURN_ORDER),
    CG122(MaterialDocCategoryEnum.CANCEL, MaterialDocGroupEnum.CG1, "CG122" , "采购退供出库-冲销" , "+" , MaterialDocRefOrderTypeEnum.MATERIAL_DOC),
    // CG161(MaterialDocGroupEnum.CG1, "CG161", "退供出库（供应商原因，已开票，原采购订单做参照-->创建退货订单）", true, true),
    VJ101(MaterialDocCategoryEnum.IN, MaterialDocGroupEnum.CG1, "VJ101" , "供应商寄售采购入库" , "+" , MaterialDocRefOrderTypeEnum.VC_PURCHASE_ORDER),
    VJ102(MaterialDocCategoryEnum.CANCEL, MaterialDocGroupEnum.CG1, "VJ102" , "供应商寄售采购入库-冲销" , "-" , MaterialDocRefOrderTypeEnum.MATERIAL_DOC),
    RWW101(MaterialDocCategoryEnum.IN, MaterialDocGroupEnum.CG1, "RWW101" , "委外采购入库" , "+" , MaterialDocRefOrderTypeEnum.SC_PURCHASE_ORDER),
    RWW102(MaterialDocCategoryEnum.CANCEL, MaterialDocGroupEnum.CG1, "RWW102" , "委外采购入库-冲销" , "-" , MaterialDocRefOrderTypeEnum.MATERIAL_DOC),

    OLY201(MaterialDocCategoryEnum.OUT, MaterialDocGroupEnum.LY2, "OLY201" , "物料领用" , "-" , MaterialDocRefOrderTypeEnum.PICKING_OUT),
    OLY202(MaterialDocCategoryEnum.CANCEL, MaterialDocGroupEnum.LY2, "OLY202" , "物料领用-冲销" , "+" , MaterialDocRefOrderTypeEnum.MATERIAL_DOC),
    OLY221(MaterialDocCategoryEnum.IN, MaterialDocGroupEnum.LY2, "OLY221" , "物料领用退库" , "+" , MaterialDocRefOrderTypeEnum.PICKING_BACK),
    OLY222(MaterialDocCategoryEnum.CANCEL, MaterialDocGroupEnum.LY2, "OLY222" , "物料领用退库-冲销" , "-" , MaterialDocRefOrderTypeEnum.MATERIAL_DOC),

    JC201(MaterialDocCategoryEnum.OUT, MaterialDocGroupEnum.LY2, "JC201" , "借出领用出库" , "-" , MaterialDocRefOrderTypeEnum.LEND_OUT_ORDER),
    JC202(MaterialDocCategoryEnum.CANCEL, MaterialDocGroupEnum.LY2, "JC202" , "借出领用出库-冲销" , "+" , MaterialDocRefOrderTypeEnum.MATERIAL_DOC),

    JC221(MaterialDocCategoryEnum.IN, MaterialDocGroupEnum.LY2, "JC221" , "借出归还" , "+" , MaterialDocRefOrderTypeEnum.LEND_IN_ORDER),
    JC222(MaterialDocCategoryEnum.CANCEL, MaterialDocGroupEnum.LY2, "JC222" , "借出归还-冲销" , "-" , MaterialDocRefOrderTypeEnum.MATERIAL_DOC),

    RWW201(MaterialDocCategoryEnum.OUT, MaterialDocGroupEnum.LY2, "RWW201" , "委外加工物资消耗出库" , "-" , null),
    RWW202(MaterialDocCategoryEnum.CANCEL, MaterialDocGroupEnum.LY2, "RWW202" , "委外加工物资消耗出库-冲销" , "+" , MaterialDocRefOrderTypeEnum.MATERIAL_DOC),

    SC301(MaterialDocCategoryEnum.IN, MaterialDocGroupEnum.SC3, "SC301" , "生产入库" , "+" , MaterialDocRefOrderTypeEnum.PRODUCTION_ORDER),
    SC302(MaterialDocCategoryEnum.CANCEL, MaterialDocGroupEnum.SC3, "SC302" , "生产入库-冲销" , "-" , MaterialDocRefOrderTypeEnum.MATERIAL_DOC),
    SC303(MaterialDocCategoryEnum.OUT, MaterialDocGroupEnum.SC3, "SC303" , "生产投料" , "-" , null),
    SC304(MaterialDocCategoryEnum.CANCEL, MaterialDocGroupEnum.SC3, "SC304" , "生产投料-冲销" , "+" , MaterialDocRefOrderTypeEnum.MATERIAL_DOC),
    /**
     * 同一个仓库id，不同公司 - STO
     */
    DB401(MaterialDocCategoryEnum.OUT, MaterialDocGroupEnum.DB4, "DB401" , "调拨出库（不同公司的不同仓库之间）-不走IMP" , "-" , MaterialDocRefOrderTypeEnum.STOCK_TRANSFER_OUT),
    DB402(MaterialDocCategoryEnum.CANCEL, MaterialDocGroupEnum.DB4, "DB402" , "调拨出库-冲销（不同公司的不同仓库之间）" , "+" , MaterialDocRefOrderTypeEnum.MATERIAL_DOC),
    DB403(MaterialDocCategoryEnum.IN, MaterialDocGroupEnum.DB4, "DB403" , "调拨入库（不同公司的不同仓库之间）-不走IMP" , "+" , MaterialDocRefOrderTypeEnum.STOCK_TRANSFER_IN),
    DB404(MaterialDocCategoryEnum.CANCEL, MaterialDocGroupEnum.DB4, "DB404" , "调拨入库-冲销（不同公司的不同仓库之间）" , "-" , MaterialDocRefOrderTypeEnum.MATERIAL_DOC),
    /**
     * 同一个公司，不同仓库id - 不触发财务凭证
     */
    DB405(MaterialDocCategoryEnum.OUT, MaterialDocGroupEnum.DB4, "DB405" , "调拨出库（同一公司的不同仓库之间）-不走IMP" , "-" , MaterialDocRefOrderTypeEnum.STOCK_TRANSFER_OUT),
    DB406(MaterialDocCategoryEnum.CANCEL, MaterialDocGroupEnum.DB4, "DB406" , "调拨出库-冲销（同一公司的不同仓库之间）" , "+" , MaterialDocRefOrderTypeEnum.MATERIAL_DOC),
    DB407(MaterialDocCategoryEnum.IN, MaterialDocGroupEnum.DB4, "DB407" , "调拨入库（同一公司的不同仓库之间）-不走IMP" , "+" , MaterialDocRefOrderTypeEnum.STOCK_TRANSFER_IN),
    DB408(MaterialDocCategoryEnum.CANCEL, MaterialDocGroupEnum.DB4, "DB408" , "调拨入库-冲销（同一公司的不同仓库之间）" , "-" , MaterialDocRefOrderTypeEnum.MATERIAL_DOC),
    ODB409(MaterialDocCategoryEnum.INOUT, MaterialDocGroupEnum.DB4, "ODB409" , "调拨出入库（同一公司的不同仓库之间，一步法，走IMP)" , "+-" , null),
    ODB410(MaterialDocCategoryEnum.CANCEL, MaterialDocGroupEnum.DB4, "ODB410" , "调拨出入库-冲销（同一公司的不同仓库之间)" , "+-" , MaterialDocRefOrderTypeEnum.MATERIAL_DOC),
    ODB411(MaterialDocCategoryEnum.INOUT, MaterialDocGroupEnum.DB4, "ODB411" , "库内调拨出入库（仓库内不同库存地点间，一步法，走IMP)" , "+-" , null),
    ODB412(MaterialDocCategoryEnum.CANCEL, MaterialDocGroupEnum.DB4, "ODB412" , "库内调拨出入库-冲销（仓库内不同库存地点间)" , "+-" , MaterialDocRefOrderTypeEnum.MATERIAL_DOC),

    OWW411(MaterialDocCategoryEnum.INOUT, MaterialDocGroupEnum.DB4, "OWW411" , "自有库<>SC委外库之间的调拨(一步法，走IMP)" , "+-" , null),
    OWW412(MaterialDocCategoryEnum.CANCEL, MaterialDocGroupEnum.DB4, "OWW412" , "自有库<>SC委外库之间的调拨-冲销(一步法，走IMP)" , "+-" , MaterialDocRefOrderTypeEnum.MATERIAL_DOC),
    OWW413(MaterialDocCategoryEnum.INOUT, MaterialDocGroupEnum.DB4, "OWW413" , "SC委外库<>自有库之间的调拨(一步法，走IMP)" , "+-" , null),
    OWW414(MaterialDocCategoryEnum.CANCEL, MaterialDocGroupEnum.DB4, "OWW414" , "SC委外库<>自有库之间的调拨-冲销(一步法，走IMP)" , "+-" , MaterialDocRefOrderTypeEnum.MATERIAL_DOC),
    OKJ411(MaterialDocCategoryEnum.INOUT, MaterialDocGroupEnum.DB4, "OKJ411" , "自有库<>CC客户寄售库之间的调拨(一步法，走IMP)" , "+-" , null),
    OKJ412(MaterialDocCategoryEnum.CANCEL, MaterialDocGroupEnum.DB4, "OKJ412" , "自有库<>CC客户寄售库之间的调拨-冲销(一步法，走IMP)" , "+-" , MaterialDocRefOrderTypeEnum.MATERIAL_DOC),
    OKJ413(MaterialDocCategoryEnum.INOUT, MaterialDocGroupEnum.DB4, "OKJ413" , "CC客户寄售库<>自有库之间的调拨(一步法，走IMP)" , "+-" , null),
    OKJ414(MaterialDocCategoryEnum.CANCEL, MaterialDocGroupEnum.DB4, "OKJ414" , "CC客户寄售库<>自有库之间的调拨-冲销(一步法，走IMP)" , "+-" , MaterialDocRefOrderTypeEnum.MATERIAL_DOC),
    OGJ411(MaterialDocCategoryEnum.INOUT, MaterialDocGroupEnum.DB4, "OGJ411" , "VC供应商寄售库<>自有库之间的调拨(一步法，走IMP)" , "+-" , null),
    OGJ412(MaterialDocCategoryEnum.CANCEL, MaterialDocGroupEnum.DB4, "OGJ412" , "VC供应商寄售库<>自有库之间的调拨-冲销(一步法，走IMP)" , "+-" , MaterialDocRefOrderTypeEnum.MATERIAL_DOC),
    OGJ413(MaterialDocCategoryEnum.INOUT, MaterialDocGroupEnum.DB4, "OGJ413" , "自有库<>VC供应商寄售库之间的调拨(一步法，走IMP)" , "+-" , null),
    OGJ414(MaterialDocCategoryEnum.CANCEL, MaterialDocGroupEnum.DB4, "OGJ414" , "自有库<>VC供应商寄售库之间的调拨-冲销(一步法，走IMP)" , "+-" , MaterialDocRefOrderTypeEnum.MATERIAL_DOC),

    ZY501(MaterialDocCategoryEnum.INOUT, MaterialDocGroupEnum.ZY5, "ZY501" , "库存转移-良品>质检品(同一库存地点)" , "+-"),
    ZY502(MaterialDocCategoryEnum.INOUT, MaterialDocGroupEnum.ZY5, "ZY502" , "库存转移-质检品>良品(同一库存地点)" , "+-"),
    ZY503(MaterialDocCategoryEnum.INOUT, MaterialDocGroupEnum.ZY5, "ZY503" , "库存转移-质检品>残次品(同一库存地点)" , "+-"),
    ZY504(MaterialDocCategoryEnum.INOUT, MaterialDocGroupEnum.ZY5, "ZY504" , "库存转移-残次品>质检品(同一库存地点)" , "+-"),
    ZY505(MaterialDocCategoryEnum.INOUT, MaterialDocGroupEnum.ZY5, "ZY505" , "库存转移-良品>残次品(同一库存地点)" , "+-"),
    ZY506(MaterialDocCategoryEnum.INOUT, MaterialDocGroupEnum.ZY5, "ZY506" , "库存转移-残次品>良品(同一库存地点)" , "+-"),

    XS601(MaterialDocCategoryEnum.OUT, MaterialDocGroupEnum.XS6, "XS601" , "销售出库" , "-" , MaterialDocRefOrderTypeEnum.SALES_ORDER),
    XS602(MaterialDocCategoryEnum.CANCEL, MaterialDocGroupEnum.XS6, "XS602" , "销售出库-撤销" , "+" , MaterialDocRefOrderTypeEnum.MATERIAL_DOC),
    XS621(MaterialDocCategoryEnum.IN, MaterialDocGroupEnum.XS6, "XS621" , "销售退货" , "+" , MaterialDocRefOrderTypeEnum.SALES_RETURN_ORDER),
    XS622(MaterialDocCategoryEnum.CANCEL, MaterialDocGroupEnum.XS6, "XS622" , "销售退货-撤销" , "-" , MaterialDocRefOrderTypeEnum.MATERIAL_DOC),

    PY701(MaterialDocCategoryEnum.IN, MaterialDocGroupEnum.TZ7, "PY701" , "盘盈" , "+" , null),
    PY702(MaterialDocCategoryEnum.CANCEL, MaterialDocGroupEnum.TZ7, "PY702" , "盘盈-撤销" , "-" , MaterialDocRefOrderTypeEnum.MATERIAL_DOC),
    PK703(MaterialDocCategoryEnum.OUT, MaterialDocGroupEnum.TZ7, "PK703" , "盘亏" , "-" , null),
    PK704(MaterialDocCategoryEnum.CANCEL, MaterialDocGroupEnum.TZ7, "PK704" , "盘亏-撤销" , "+" , MaterialDocRefOrderTypeEnum.MATERIAL_DOC),
    OBF705(MaterialDocCategoryEnum.OUT, MaterialDocGroupEnum.TZ7, "OBF705" , "报废出库" , "-" , null),
    OBF706(MaterialDocCategoryEnum.CANCEL, MaterialDocGroupEnum.TZ7, "OBF706" , "报废出库-冲销" , "+" , MaterialDocRefOrderTypeEnum.MATERIAL_DOC),
    OZP707(MaterialDocCategoryEnum.IN, MaterialDocGroupEnum.TZ7, "OZP707" , "赠品入库" , "+" , MaterialDocRefOrderTypeEnum.GIVEAWAY_IN),
    OZP708(MaterialDocCategoryEnum.CANCEL, MaterialDocGroupEnum.TZ7, "OZP708" , "赠品入库-冲销" , "-" , MaterialDocRefOrderTypeEnum.MATERIAL_DOC),
    OZP721(MaterialDocCategoryEnum.OUT, MaterialDocGroupEnum.TZ7, "OZP721" , "赠品退货" , "-" , MaterialDocRefOrderTypeEnum.GIVEAWAY_BACK),
    OZP722(MaterialDocCategoryEnum.CANCEL, MaterialDocGroupEnum.TZ7, "OZP722" , "赠品退货-冲销" , "+" , MaterialDocRefOrderTypeEnum.MATERIAL_DOC),
    TZ709(MaterialDocCategoryEnum.IN, MaterialDocGroupEnum.TZ7, "TZ709" , "库存调整单-入库" , "+" , null),
    TZ710(MaterialDocCategoryEnum.CANCEL, MaterialDocGroupEnum.TZ7, "TZ710" , "库存调整单-入库-冲销" , "-" , MaterialDocRefOrderTypeEnum.MATERIAL_DOC),
    TZ711(MaterialDocCategoryEnum.OUT, MaterialDocGroupEnum.TZ7, "TZ711" , "库存调整单-出库" , "-" , null),
    TZ712(MaterialDocCategoryEnum.CANCEL, MaterialDocGroupEnum.TZ7, "TZ712" , "库存调整单-出库-冲销" , "+" , MaterialDocRefOrderTypeEnum.MATERIAL_DOC),

    WL801(MaterialDocCategoryEnum.IN, MaterialDocGroupEnum.WL8, "WL801" , "物料价格调整" , "" , null),
    WL802(MaterialDocCategoryEnum.CANCEL, MaterialDocGroupEnum.WL8, "WL802" , "物料价格调整-冲销" , "" , MaterialDocRefOrderTypeEnum.MATERIAL_DOC),
    WL803(MaterialDocCategoryEnum.INOUT, MaterialDocGroupEnum.WL8, "WL803" , "同公司不同物料间的转换-一步法" , "+-" , null),
    WL804(MaterialDocCategoryEnum.CANCEL, MaterialDocGroupEnum.WL8, "WL804" , "同公司不同物料间的转换-一步法-冲销" , "+-" , MaterialDocRefOrderTypeEnum.MATERIAL_DOC),

    FP901(MaterialDocCategoryEnum.IN, MaterialDocGroupEnum.FP9, "FP901" , "供应商发票校验" , "" , null),
    FP902(MaterialDocCategoryEnum.CANCEL, MaterialDocGroupEnum.FP9, "FP902" , "供应商发票校验-冲销" , "" , MaterialDocRefOrderTypeEnum.MATERIAL_DOC),
    FP903(MaterialDocCategoryEnum.IN, MaterialDocGroupEnum.FP9, "FP903" , "供应商借项调整" , "" , null),
    FP904(MaterialDocCategoryEnum.CANCEL, MaterialDocGroupEnum.FP9, "FP904" , "供应商借项调整-冲销" , "" , MaterialDocRefOrderTypeEnum.MATERIAL_DOC),
    FP905(MaterialDocCategoryEnum.IN, MaterialDocGroupEnum.FP9, "FP905" , "供应商贷项调整" , "" , null),
    FP906(MaterialDocCategoryEnum.CANCEL, MaterialDocGroupEnum.FP9, "FP906" , "供应商贷项调整-冲销" , "" , MaterialDocRefOrderTypeEnum.MATERIAL_DOC),

    TEST(MaterialDocCategoryEnum.IN, MaterialDocGroupEnum.QC0, "TEST" , "测试专用(不验证必填项)" , ""),

    ;

    private MaterialDocCategoryEnum materialDocCategoryEnum;
    private MaterialDocGroupEnum materialDocGroupEnum;
    private final String code;
    private final String desc;
    private String io;
    /**
     * 描述 参考业务单据类型
     */
    private MaterialDocRefOrderTypeEnum refOrderTypeEnum;

    MaterialAdjustTypeEnum(MaterialDocCategoryEnum materialDocCategoryEnum, MaterialDocGroupEnum materialDocGroupEnum, String code, String desc, String io) {
        this.code = code;
        this.desc = desc;
        this.io = io;
        this.materialDocCategoryEnum = materialDocCategoryEnum;
        this.materialDocGroupEnum = materialDocGroupEnum;
    }

    public static Boolean isExist(String code) {
        return Arrays.stream(MaterialAdjustTypeEnum.values()).anyMatch(v -> v.getCode().equals(code));
    }
}
