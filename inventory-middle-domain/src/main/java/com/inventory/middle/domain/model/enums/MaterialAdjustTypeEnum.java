package com.inventory.middle.domain.model.enums;

import java.util.Arrays;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import top.kdla.framework.dto.IEnum;

/**
 * @author kll 物料凭证移动类型枚举
 *         <p>
 */
@AllArgsConstructor
@Getter
public enum MaterialAdjustTypeEnum implements IEnum<String> {
    OQC101(MaterialDocGroupEnum.QC0, "OQC101", "期初库存同步", true, true, "materialDocInAddHandleChain", "OQC102", null,
        MaterialDocRefTypeEnum.NO, null),
    OQC102(MaterialDocGroupEnum.QC0, "OQC102", "期初库存同步-撤销", true, true, "", "", MaterialDocCategoryEnum.IN,
        MaterialDocRefTypeEnum.YES, MaterialDocRefOrderTypeEnum.MATERIAL_DOC),

    CG101(MaterialDocGroupEnum.CG1, "CG101", "采购入库", true, true, "materialDocInAddHandleChain", "CG102", null,
        MaterialDocRefTypeEnum.ALL, MaterialDocRefOrderTypeEnum.PURCHASE_ORDER),
    CG102(MaterialDocGroupEnum.CG1, "CG102", "采购入库-冲销（仓库操作失误）", true, true, "", "", MaterialDocCategoryEnum.IN,
        MaterialDocRefTypeEnum.YES, MaterialDocRefOrderTypeEnum.MATERIAL_DOC),
    CG121(MaterialDocGroupEnum.CG1, "CG121", "采购退供出库", true, true, "materialDocOutAcrossHandleChain", "CG122", null, MaterialDocRefTypeEnum.ALL,
        MaterialDocRefOrderTypeEnum.PURCHASE_RETURN_ORDER),
    CG122(MaterialDocGroupEnum.CG1, "CG122", "采购退供出库-冲销", true, true, "", "", MaterialDocCategoryEnum.OUT,
        MaterialDocRefTypeEnum.YES, MaterialDocRefOrderTypeEnum.MATERIAL_DOC),
    // CG161(MaterialDocGroupEnum.CG1, "CG161", "退供出库（供应商原因，已开票，原采购订单做参照-->创建退货订单）", true, true, "", "CG162"),
    VJ101(MaterialDocGroupEnum.CG1, "VJ101", "供应商寄售采购入库", true, true, "materialDocInAddHandleChain", "VJ102", null,
        MaterialDocRefTypeEnum.ALL, MaterialDocRefOrderTypeEnum.VC_PURCHASE_ORDER),
    VJ102(MaterialDocGroupEnum.CG1, "VJ102", "供应商寄售采购入库-冲销", true, true, "", "", MaterialDocCategoryEnum.IN,
        MaterialDocRefTypeEnum.YES, MaterialDocRefOrderTypeEnum.MATERIAL_DOC),
    RWW101(MaterialDocGroupEnum.CG1, "RWW101", "委外采购入库", true, true, "materialDocInAddHandleChain", "RWW102", null,
        MaterialDocRefTypeEnum.ALL, MaterialDocRefOrderTypeEnum.SC_PURCHASE_ORDER),
    RWW102(MaterialDocGroupEnum.CG1, "RWW102", "委外采购入库-冲销", true, true, "", "", MaterialDocCategoryEnum.IN,
        MaterialDocRefTypeEnum.YES, MaterialDocRefOrderTypeEnum.MATERIAL_DOC),

    OLY201(MaterialDocGroupEnum.LY2, "OLY201", "物料领用出库", true, true, "materialDocOutAcrossHandleChain", "OLY202", null,
        MaterialDocRefTypeEnum.NO, null),
    OLY202(MaterialDocGroupEnum.LY2, "OLY202", "物料领用出库-冲销", true, true, "", "", MaterialDocCategoryEnum.OUT,
        MaterialDocRefTypeEnum.YES, MaterialDocRefOrderTypeEnum.MATERIAL_DOC),

    JC201(MaterialDocGroupEnum.LY2, "JC201", "借出领用出库", true, true, "materialDocOutAcrossHandleChain", "JC202", null, MaterialDocRefTypeEnum.ALL,
        MaterialDocRefOrderTypeEnum.LEND_OUT_ORDER),
    JC202(MaterialDocGroupEnum.LY2, "JC202", "借出领用出库-冲销", true, true, "", "", MaterialDocCategoryEnum.OUT,
        MaterialDocRefTypeEnum.YES, MaterialDocRefOrderTypeEnum.MATERIAL_DOC),

    JC221(MaterialDocGroupEnum.LY2, "JC221", "借出归还入库", true, true, "materialDocInOriginalHandleChain", "JC222", null,
        MaterialDocRefTypeEnum.ALL, MaterialDocRefOrderTypeEnum.LEND_IN_ORDER),
    JC222(MaterialDocGroupEnum.LY2, "JC222", "借出归还入库-冲销", true, true, "", "", MaterialDocCategoryEnum.IN,
        MaterialDocRefTypeEnum.YES, MaterialDocRefOrderTypeEnum.MATERIAL_DOC),

    RWW201(MaterialDocGroupEnum.LY2, "RWW201", "委外加工物资消耗出库", true, true, "materialDocOutAcrossHandleChain", "RWW202", null, MaterialDocRefTypeEnum.NO,
        null),
    RWW202(MaterialDocGroupEnum.LY2, "RWW202", "委外加工物资消耗出库-冲销", true, true, "", "", MaterialDocCategoryEnum.OUT,
        MaterialDocRefTypeEnum.YES, MaterialDocRefOrderTypeEnum.MATERIAL_DOC),

    SC301(MaterialDocGroupEnum.SC3, "SC301", "生产入库", true, true, "materialDocInAddHandleChain", "SC302", null,
        MaterialDocRefTypeEnum.ALL, MaterialDocRefOrderTypeEnum.PRODUCTION_ORDER),
    SC302(MaterialDocGroupEnum.SC3, "SC302", "生产入库-冲销", true, true, "", "", MaterialDocCategoryEnum.IN,
        MaterialDocRefTypeEnum.YES, MaterialDocRefOrderTypeEnum.MATERIAL_DOC),
    SC303(MaterialDocGroupEnum.SC3, "SC303", "生产投料出库", true, true, "materialDocOutAcrossHandleChain", "SC304", null, MaterialDocRefTypeEnum.NO, null),
    SC304(MaterialDocGroupEnum.SC3, "SC304", "生产投料出库-冲销", true, true, "", "", MaterialDocCategoryEnum.OUT,
        MaterialDocRefTypeEnum.YES, MaterialDocRefOrderTypeEnum.MATERIAL_DOC),

    DB401(MaterialDocGroupEnum.DB4, "DB401", "调拨出库（不同公司的不同仓库之间）-不走IMP", true, true, "materialDocOutAcrossHandleChain", "DB402", null,
        MaterialDocRefTypeEnum.ALL, MaterialDocRefOrderTypeEnum.STOCK_TRANSFER_OUT),
    DB402(MaterialDocGroupEnum.DB4, "DB402", "调拨出库-冲销（不同公司的不同仓库之间）", true, true, "", "", MaterialDocCategoryEnum.OUT,
        MaterialDocRefTypeEnum.YES, MaterialDocRefOrderTypeEnum.MATERIAL_DOC),
    DB403(MaterialDocGroupEnum.DB4, "DB403", "调拨入库（不同公司的不同仓库之间）-不走IMP", true, true, "materialDocInAddHandleChain",
        "DB404", null, MaterialDocRefTypeEnum.ALL, MaterialDocRefOrderTypeEnum.STOCK_TRANSFER_IN),
    DB404(MaterialDocGroupEnum.DB4, "DB404", "调拨入库-冲销（不同公司的不同仓库之间）", true, true, "", "", MaterialDocCategoryEnum.IN,
        MaterialDocRefTypeEnum.YES, MaterialDocRefOrderTypeEnum.MATERIAL_DOC),
    DB405(MaterialDocGroupEnum.DB4, "DB405", "调拨出库（同一公司的不同仓库之间）-不走IMP", true, false, "materialDocOutAcrossHandleChain", "DB406", null,
        MaterialDocRefTypeEnum.ALL, MaterialDocRefOrderTypeEnum.STOCK_TRANSFER_OUT),
    DB406(MaterialDocGroupEnum.DB4, "DB406", "调拨出库-冲销（同一公司的不同仓库之间）", true, false, "", "", MaterialDocCategoryEnum.OUT,
        MaterialDocRefTypeEnum.YES, MaterialDocRefOrderTypeEnum.MATERIAL_DOC),
    DB407(MaterialDocGroupEnum.DB4, "DB407", "调拨入库（同一公司的不同仓库之间）-不走IMP", true, false, "materialDocInAddHandleChain",
        "DB408", null, MaterialDocRefTypeEnum.ALL, MaterialDocRefOrderTypeEnum.STOCK_TRANSFER_IN),
    DB408(MaterialDocGroupEnum.DB4, "DB408", "调拨入库-冲销（同一公司的不同仓库之间）", true, false, "", "", MaterialDocCategoryEnum.IN,
        MaterialDocRefTypeEnum.YES, MaterialDocRefOrderTypeEnum.MATERIAL_DOC),

    ODB409(MaterialDocGroupEnum.DB4, "ODB409", "调拨出入库（同一公司的不同仓库之间，一步法，走IMP)", true, false,
        "materialDocInOutAddHandleChain", "ODB410", null, MaterialDocRefTypeEnum.NO, null),
    ODB410(MaterialDocGroupEnum.DB4, "ODB410", "调拨出入库-冲销（同一公司的不同仓库之间)", true, false, "", "",
        MaterialDocCategoryEnum.INOUT, MaterialDocRefTypeEnum.YES, MaterialDocRefOrderTypeEnum.MATERIAL_DOC),
    ODB411(MaterialDocGroupEnum.DB4, "ODB411", "库内调拨出入库（仓库内不同库存地点间，一步法，走IMP)", false, false,
        "materialDocInOutAddHandleChain", "ODB412", null, MaterialDocRefTypeEnum.NO, null),
    ODB412(MaterialDocGroupEnum.DB4, "ODB412", "库内调拨出入库-冲销（仓库内不同库存地点间)", false, false, "", "",
        MaterialDocCategoryEnum.INOUT, MaterialDocRefTypeEnum.YES, MaterialDocRefOrderTypeEnum.MATERIAL_DOC),

    OWW411(MaterialDocGroupEnum.DB4, "OWW411", "自有库<>SC委外库之间的调拨(一步法，走IMP)", false, true,
        "materialDocInOutAddHandleChain", "OWW412", null, MaterialDocRefTypeEnum.NO, null),
    OWW412(MaterialDocGroupEnum.DB4, "OWW412", "自有库<>SC委外库之间的调拨-冲销(一步法，走IMP)", false, true, "", "", null,
        MaterialDocRefTypeEnum.YES, MaterialDocRefOrderTypeEnum.MATERIAL_DOC),
    OWW413(MaterialDocGroupEnum.DB4, "OWW413", "SC委外库<>自有库之间的调拨(一步法，走IMP)", false, true,
        "materialDocInOutAddHandleChain", "OWW414", null, MaterialDocRefTypeEnum.NO, null),
    OWW414(MaterialDocGroupEnum.DB4, "OWW414", "SC委外库<>自有库之间的调拨-冲销(一步法，走IMP)", false, true, "", "", null,
        MaterialDocRefTypeEnum.YES, MaterialDocRefOrderTypeEnum.MATERIAL_DOC),
    OKJ411(MaterialDocGroupEnum.DB4, "OKJ411", "自有库<>CC客户寄售库之间的调拨(一步法，走IMP)", false, true,
        "materialDocInOutAddHandleChain", "OKJ412", null, MaterialDocRefTypeEnum.NO, null),
    OKJ412(MaterialDocGroupEnum.DB4, "OKJ412", "自有库<>CC客户寄售库之间的调拨-冲销(一步法，走IMP)", false, true, "", "", null,
        MaterialDocRefTypeEnum.YES, MaterialDocRefOrderTypeEnum.MATERIAL_DOC),
    OKJ413(MaterialDocGroupEnum.DB4, "OKJ413", "CC客户寄售库<>自有库之间的调拨(一步法，走IMP)", false, true,
        "materialDocInOutAddHandleChain", "OKJ414", null, MaterialDocRefTypeEnum.NO, null),
    OKJ414(MaterialDocGroupEnum.DB4, "OKJ414", "CC客户寄售库<>自有库之间的调拨-冲销(一步法，走IMP)", false, true, "", "", null,
        MaterialDocRefTypeEnum.YES, MaterialDocRefOrderTypeEnum.MATERIAL_DOC),
    OGJ411(MaterialDocGroupEnum.DB4, "OGJ411", "VC供应商寄售库<>自有库之间的调拨(一步法，走IMP)", true, true,
        "materialDocInOutAddHandleChain", "OGJ412", null, MaterialDocRefTypeEnum.NO, null),
    OGJ412(MaterialDocGroupEnum.DB4, "OGJ412", "VC供应商寄售库<>自有库之间的调拨-冲销(一步法，走IMP)", true, true, "", "", null,
        MaterialDocRefTypeEnum.YES, MaterialDocRefOrderTypeEnum.MATERIAL_DOC),
    OGJ413(MaterialDocGroupEnum.DB4, "OGJ413", "自有库<>VC供应商寄售库之间的调拨(一步法，走IMP)", true, true,
        "materialDocInOutAddHandleChain", "OGJ414", null, MaterialDocRefTypeEnum.NO, null),
    OGJ414(MaterialDocGroupEnum.DB4, "OGJ414", "自有库<>VC供应商寄售库之间的调拨-冲销(一步法，走IMP)", true, true, "", "", null,
        MaterialDocRefTypeEnum.YES, MaterialDocRefOrderTypeEnum.MATERIAL_DOC),

    ZY501(MaterialDocGroupEnum.ZY5, "ZY501", "库存转移-良品>质检品(同一库存地点)", false, true, "materialDocInOutOriginalHandleChain",
        "", null, MaterialDocRefTypeEnum.NO, null),
    ZY502(MaterialDocGroupEnum.ZY5, "ZY502", "库存转移-质检品>良品(同一库存地点)", false, true, "materialDocInOutOriginalHandleChain",
        "", null, MaterialDocRefTypeEnum.NO, null),
    ZY503(MaterialDocGroupEnum.ZY5, "ZY503", "库存转移-质检品>残次品(同一库存地点)", false, true, "materialDocInOutOriginalHandleChain",
        "", null, MaterialDocRefTypeEnum.NO, null),
    ZY504(MaterialDocGroupEnum.ZY5, "ZY504", "库存转移-残次品>质检品(同一库存地点)", false, true, "materialDocInOutOriginalHandleChain",
        "", null, MaterialDocRefTypeEnum.NO, null),
    ZY505(MaterialDocGroupEnum.ZY5, "ZY505", "库存转移-良品>残次品(同一库存地点)", false, true, "materialDocInOutOriginalHandleChain",
        "", null, MaterialDocRefTypeEnum.NO, null),
    ZY506(MaterialDocGroupEnum.ZY5, "ZY506", "库存转移-残次品>良品(同一库存地点)", false, true, "materialDocInOutOriginalHandleChain",
        "", null, MaterialDocRefTypeEnum.NO, null),

    XS601(MaterialDocGroupEnum.XS6, "XS601", "销售出库", true, true, "materialDocOutAcrossHandleChain", "XS602", null, MaterialDocRefTypeEnum.ALL,
        MaterialDocRefOrderTypeEnum.SALES_ORDER),
    XS602(MaterialDocGroupEnum.XS6, "XS602", "销售出库-撤销", true, true, "", "", MaterialDocCategoryEnum.OUT,
        MaterialDocRefTypeEnum.YES, MaterialDocRefOrderTypeEnum.MATERIAL_DOC),
    XS621(MaterialDocGroupEnum.XS6, "XS621", "销售退货入库", true, true, "materialDocInAddHandleChain", "XS622", null,
        MaterialDocRefTypeEnum.ALL, MaterialDocRefOrderTypeEnum.SALES_RETURN_ORDER),
    XS622(MaterialDocGroupEnum.XS6, "XS622", "销售退货入库-撤销", true, true, "", "", MaterialDocCategoryEnum.IN,
        MaterialDocRefTypeEnum.YES, MaterialDocRefOrderTypeEnum.MATERIAL_DOC),

    PY701(MaterialDocGroupEnum.TZ7, "PY701", "盘盈", true, true, "materialDocInOriginalHandleChain", "PY702", null,
        MaterialDocRefTypeEnum.NO, null),
    PY702(MaterialDocGroupEnum.TZ7, "PY702", "盘盈-撤销", true, true, "", "", MaterialDocCategoryEnum.IN,
        MaterialDocRefTypeEnum.YES, MaterialDocRefOrderTypeEnum.MATERIAL_DOC),
    PK703(MaterialDocGroupEnum.TZ7, "PK703", "盘亏", true, true, "materialDocOutOriginalHandleChain", "PK704", null, MaterialDocRefTypeEnum.NO, null),
    PK704(MaterialDocGroupEnum.TZ7, "PK704", "盘亏-撤销", true, true, "", "", MaterialDocCategoryEnum.OUT,
        MaterialDocRefTypeEnum.YES, MaterialDocRefOrderTypeEnum.MATERIAL_DOC),
    OBF705(MaterialDocGroupEnum.TZ7, "OBF705", "报废出库", true, true, "materialDocOutAcrossHandleChain", "OBF706", null, MaterialDocRefTypeEnum.NO, null),
    OBF706(MaterialDocGroupEnum.TZ7, "OBF706", "报废出库-冲销", true, true, "", "", MaterialDocCategoryEnum.OUT,
        MaterialDocRefTypeEnum.YES, MaterialDocRefOrderTypeEnum.MATERIAL_DOC),

    OZP707(MaterialDocGroupEnum.TZ7, "OZP707", "赠品入库", true, true, "materialDocInAddHandleChain", "OZP708",
        MaterialDocCategoryEnum.IN, MaterialDocRefTypeEnum.ALL, MaterialDocRefOrderTypeEnum.PURCHASE_ORDER),
    OZP708(MaterialDocGroupEnum.TZ7, "OZP708", "赠品入库-冲销", true, true, "", "", MaterialDocCategoryEnum.IN,
        MaterialDocRefTypeEnum.YES, MaterialDocRefOrderTypeEnum.MATERIAL_DOC),
    TZ709(MaterialDocGroupEnum.TZ7, "TZ709", "库存调整单-入库", true, true, "materialDocInOriginalHandleChain", "TZ710",
        MaterialDocCategoryEnum.IN, MaterialDocRefTypeEnum.NO, null),
    TZ710(MaterialDocGroupEnum.TZ7, "TZ710", "库存调整单-入库-冲销", true, true, "", "", MaterialDocCategoryEnum.IN,
        MaterialDocRefTypeEnum.YES, MaterialDocRefOrderTypeEnum.MATERIAL_DOC),
    TZ711(MaterialDocGroupEnum.TZ7, "TZ711", "库存调整单-出库", true, true, "materialDocOutAcrossHandleChain", "TZ712", null, MaterialDocRefTypeEnum.NO,
        null),
    TZ712(MaterialDocGroupEnum.TZ7, "TZ712", "库存调整单-出库-冲销", true, true, "", "", MaterialDocCategoryEnum.OUT,
        MaterialDocRefTypeEnum.YES, MaterialDocRefOrderTypeEnum.MATERIAL_DOC),

    WL801(MaterialDocGroupEnum.WL8, "WL801", "物料价格调整", true, true, "", "WL802", null, MaterialDocRefTypeEnum.NO, null),
    WL802(MaterialDocGroupEnum.WL8, "WL802", "物料价格调整-冲销", true, true, "", "", null, MaterialDocRefTypeEnum.YES,
        MaterialDocRefOrderTypeEnum.MATERIAL_DOC),
    WL803(MaterialDocGroupEnum.WL8, "WL803", "同公司不同物料间的转换-一步法", true, true, "", "WL804", null,
        MaterialDocRefTypeEnum.NO, null),
    WL804(MaterialDocGroupEnum.WL8, "WL804", "同公司不同物料间的转换-一步法-冲销", true, true, "", "", null, MaterialDocRefTypeEnum.YES,
        MaterialDocRefOrderTypeEnum.MATERIAL_DOC),

    FP901(MaterialDocGroupEnum.FP9, "FP901", "供应商发票校验", true, true, "", "FP902", null, MaterialDocRefTypeEnum.NO, null),
    FP902(MaterialDocGroupEnum.FP9, "FP902", "供应商发票校验-冲销", true, true, "", "", null, MaterialDocRefTypeEnum.YES,
        MaterialDocRefOrderTypeEnum.MATERIAL_DOC),
    FP903(MaterialDocGroupEnum.FP9, "FP903", "供应商借项调整", true, true, "", "FP904", null, MaterialDocRefTypeEnum.NO, null),
    FP904(MaterialDocGroupEnum.FP9, "FP904", "供应商借项调整-冲销", true, true, "", "", null, MaterialDocRefTypeEnum.YES,
        MaterialDocRefOrderTypeEnum.MATERIAL_DOC),
    FP905(MaterialDocGroupEnum.FP9, "FP905", "供应商贷项调整", true, true, "", "FP906", null, MaterialDocRefTypeEnum.NO, null),
    FP906(MaterialDocGroupEnum.FP9, "FP906", "供应商贷项调整-冲销", true, true, "", "", null, MaterialDocRefTypeEnum.YES,
        MaterialDocRefOrderTypeEnum.MATERIAL_DOC),

    TEST(MaterialDocGroupEnum.QC0, "TEST", "测试专用(不验证必填项)", true, false, "", "", null, MaterialDocRefTypeEnum.ALL,
        null),;

    private MaterialDocGroupEnum materialDocGroupEnum;
    private final String code;
    private final String desc;
    /**
     * 是否计算移动平均价
     */
    private final boolean calMAP;
    /**
     * 是否触发财务凭证
     */
    private final boolean triggerFinanceVoucher;
    /**
     * 处理的bean
     */
    private String beanName;

    /**
     * 移动类型对应的冲销编码
     */
    private String reverseCode;

    /**
     * 移动类型对应的大类 出、入、出入
     */
    private MaterialDocCategoryEnum materialDocCategoryEnum;

    /**
     * 描述 是否可以参考业务单据
     */
    private MaterialDocRefTypeEnum materialDocRefTypeEnum;

    /**
     * 描述 参考业务单据类型
     */
    private MaterialDocRefOrderTypeEnum materialDocRefOrderTypeEnum;

    public static String valueByCode(String code) {
        if (code == null) {
            return "";
        }
        return Arrays.asList(values()).stream().filter(e -> code.equals(e.getCode()))
            .map(MaterialAdjustTypeEnum::getDesc).findFirst().orElse("");
    }

    public static boolean isValidEnum(String code) {
        if (code == null) {
            return false;
        }
        for (MaterialAdjustTypeEnum materialAdjustTypeEnum : MaterialAdjustTypeEnum.values()) {
            if (materialAdjustTypeEnum.getCode().equals(code)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param code
     * @return
     */
    public static MaterialAdjustTypeEnum tansfer(String code) {
        for (MaterialAdjustTypeEnum materialAdjustTypeEnum : MaterialAdjustTypeEnum.values()) {
            if (materialAdjustTypeEnum.getCode().equals(code)) {
                return materialAdjustTypeEnum;
            }
        }
        return null;
    }

    public static MaterialDocGroupEnum getMaterialDocGroup(String code) {
        MaterialAdjustTypeEnum adjustTypeEnum = tansfer(code);
        return Objects.nonNull(adjustTypeEnum) ? adjustTypeEnum.getMaterialDocGroupEnum() : null;
    }

    public static String beanNameByCode(String code) {
        if (null == code) {
            return "";
        }
        return Arrays.asList(values()).stream().filter(e -> code.equals(e.getCode()))
            .map(MaterialAdjustTypeEnum::getBeanName).findFirst().orElse("");
    }

    public static boolean checkByCode(String code) {
        return isValidEnum(code);
    }

}
