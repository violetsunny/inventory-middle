/**
 * OYO.com Inc.
 * Copyright (c) 2017-2019 All Rights Reserved.
 */
package com.inventory.middle.domain.model.enums;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.Lists;

import lombok.AllArgsConstructor;
import lombok.Getter;
import top.kdla.framework.dto.IEnum;

/**
 * @author zhou.shengyu
 * @version $Id: BusinessTypeEnum, v 0.1 2020-01-10 13:56 oyo Exp $
 */
@AllArgsConstructor
@Getter
public enum BusinessTypeEnum implements IEnum<String> {
    /***入库 + 冲销**/
    INIT("INIT", "期初导入", MaterialAdjustTypeEnum.OQC101, MaterialDocCategoryEnum.IN, "+",
        Arrays.asList(MaterialDocSourceEnum.IMP, MaterialDocSourceEnum.INTERFACE)),
    INIT_CANCEL("INIT_CANCEL", "期初导入-撤销", MaterialAdjustTypeEnum.OQC102, MaterialDocCategoryEnum.CANCEL, "-",
        Arrays.asList(MaterialDocSourceEnum.IMP, MaterialDocSourceEnum.INTERFACE)),

    GIVEAWAY("GIVEAWAY", "赠品入库", MaterialAdjustTypeEnum.OZP707, MaterialDocCategoryEnum.IN, "+",
        Arrays.asList(MaterialDocSourceEnum.IMP, MaterialDocSourceEnum.INTERFACE)),
    GIVEAWAY_CANCEL("GIVEAWAY_CANCEL", "赠品入库-撤销", MaterialAdjustTypeEnum.OZP708, MaterialDocCategoryEnum.CANCEL, "-",
        Arrays.asList(MaterialDocSourceEnum.IMP, MaterialDocSourceEnum.INTERFACE)),

    PURCHASE_INVENTORY_IN("PURCHASE_INVENTORY_IN", "采购入库", MaterialAdjustTypeEnum.CG101, MaterialDocCategoryEnum.IN, "+",
        Arrays.asList(MaterialDocSourceEnum.IMP, MaterialDocSourceEnum.INTERFACE)),
    PURCHASE_INVENTORY_IN_CANCEL("PURCHASE_INVENTORY_IN_CANCEL", "采购入库-撤销", MaterialAdjustTypeEnum.CG102, MaterialDocCategoryEnum.CANCEL, "-",
        Arrays.asList(MaterialDocSourceEnum.IMP, MaterialDocSourceEnum.INTERFACE)),

    PRODUCE("PRODUCE", "生产入库", MaterialAdjustTypeEnum.SC301, MaterialDocCategoryEnum.IN, "+",
        Arrays.asList(MaterialDocSourceEnum.IMP, MaterialDocSourceEnum.INTERFACE)),
    PRODUCE_CANCEL("PRODUCE_CANCEL", "生产入库-撤销", MaterialAdjustTypeEnum.SC302, MaterialDocCategoryEnum.CANCEL, "-",
        Arrays.asList(MaterialDocSourceEnum.IMP, MaterialDocSourceEnum.INTERFACE)),

    RETURN_FROM_BORROW("RETURN_FROM_BORROW", "借出归还", MaterialAdjustTypeEnum.JC221, MaterialDocCategoryEnum.IN, "+",
        Arrays.asList(MaterialDocSourceEnum.IMP, MaterialDocSourceEnum.INTERFACE)),
    RETURN_FROM_BORROW_CANCEL("RETURN_FROM_BORROW_CANCEL", "借出归还-撤销", MaterialAdjustTypeEnum.JC222, MaterialDocCategoryEnum.CANCEL, "-",
        Arrays.asList(MaterialDocSourceEnum.IMP, MaterialDocSourceEnum.INTERFACE)),

    RETURN_FROM_SALE("RETURN_FROM_SALE", "销售退货", MaterialAdjustTypeEnum.XS621, MaterialDocCategoryEnum.IN, "+",
        Arrays.asList(MaterialDocSourceEnum.IMP, MaterialDocSourceEnum.INTERFACE)),
    RETURN_FROM_SALE_CANCEL("RETURN_FROM_SALE_CANCEL", "销售退货-撤销", MaterialAdjustTypeEnum.XS622, MaterialDocCategoryEnum.CANCEL, "-",
        Arrays.asList(MaterialDocSourceEnum.IMP, MaterialDocSourceEnum.INTERFACE)),

    INVENTORY_PROFIT("INVENTORY_PROFIT", "盘盈", MaterialAdjustTypeEnum.PY701, MaterialDocCategoryEnum.IN, "+",
        Arrays.asList(MaterialDocSourceEnum.IMP, MaterialDocSourceEnum.INTERFACE)),
    INVENTORY_PROFIT_CANCEL("INVENTORY_PROFIT_CANCEL", "盘盈-撤销", MaterialAdjustTypeEnum.PY702, MaterialDocCategoryEnum.CANCEL, "-",
        Arrays.asList(MaterialDocSourceEnum.IMP, MaterialDocSourceEnum.INTERFACE)),

    INVENTORY_ADJUSTMENT_IN("INVENTORY_ADJUSTMENT_IN", "库存调整入库", MaterialAdjustTypeEnum.TZ709, MaterialDocCategoryEnum.IN, "+",
        Arrays.asList(MaterialDocSourceEnum.INTERFACE)),
    INVENTORY_ADJUSTMENT_IN_CANCEL("INVENTORY_ADJUSTMENT_IN_CANCEL", "库存调整入库-撤销", MaterialAdjustTypeEnum.TZ710, MaterialDocCategoryEnum.CANCEL, "-",
        Arrays.asList(MaterialDocSourceEnum.INTERFACE)),

    TRANSFER_SAME_COMPANY_IN("TRANSFER_SAME_COMPANY_IN", "调拨入库（同一公司的不同仓库之间）", MaterialAdjustTypeEnum.DB407, MaterialDocCategoryEnum.IN, "+",
        Arrays.asList(MaterialDocSourceEnum.INTERFACE)),
    TRANSFER_SAME_COMPANY_IN_CANCEL("TRANSFER_SAME_COMPANY_IN_CANCEL", "调拨入库-撤销（同一公司的不同仓库之间）", MaterialAdjustTypeEnum.DB408, MaterialDocCategoryEnum.CANCEL,
        "-", Arrays.asList(MaterialDocSourceEnum.INTERFACE)),

    TRANSFER_DIFFERENT_COMPANY_IN("TRANSFER_DIFFERENT_COMPANY_IN", "调拨入库（不同公司的不同仓库之间）", MaterialAdjustTypeEnum.DB403, MaterialDocCategoryEnum.IN, "+",
        Arrays.asList(MaterialDocSourceEnum.INTERFACE)),
    TRANSFER_DIFFERENT_COMPANY_IN_CANCEL("TRANSFER_DIFFERENT_COMPANY_IN_CANCEL", "调拨入库-撤销（不同公司的不同仓库之间）", MaterialAdjustTypeEnum.DB404,
        MaterialDocCategoryEnum.CANCEL, "-", Arrays.asList(MaterialDocSourceEnum.INTERFACE)),

    /***出库 + 冲销**/
    PICKING_OUT("PICKING_OUT", "领料出库", MaterialAdjustTypeEnum.OLY201, MaterialDocCategoryEnum.OUT, "-",
        Arrays.asList(MaterialDocSourceEnum.IMP, MaterialDocSourceEnum.INTERFACE)),
    PICKING_OUT_CANCEL("PICKING_OUT_CANCEL", "领料出库-撤销", MaterialAdjustTypeEnum.OLY202, MaterialDocCategoryEnum.CANCEL, "+",
        Arrays.asList(MaterialDocSourceEnum.IMP, MaterialDocSourceEnum.INTERFACE)),

    BORROW_OUT("BORROW_OUT", "借出", MaterialAdjustTypeEnum.JC201, MaterialDocCategoryEnum.OUT, "-",
        Arrays.asList(MaterialDocSourceEnum.IMP, MaterialDocSourceEnum.INTERFACE)),
    BORROW_OUT_CANCEL("BORROW_OUT_CANCEL", "借出-撤销", MaterialAdjustTypeEnum.JC202, MaterialDocCategoryEnum.CANCEL, "+",
        Arrays.asList(MaterialDocSourceEnum.IMP, MaterialDocSourceEnum.INTERFACE)),

    SALE("SALE", "销售", MaterialAdjustTypeEnum.XS601, MaterialDocCategoryEnum.OUT, "-",
        Arrays.asList(MaterialDocSourceEnum.IMP, MaterialDocSourceEnum.INTERFACE)),
    SALE_CANCEL("SALE_CANCEL", "销售-撤销", MaterialAdjustTypeEnum.XS602, MaterialDocCategoryEnum.CANCEL, "+",
        Arrays.asList(MaterialDocSourceEnum.IMP, MaterialDocSourceEnum.INTERFACE)),

    RETURN_FROM_PURCHASE("RETURN_FROM_PURCHASE", "采购退供", MaterialAdjustTypeEnum.CG121, MaterialDocCategoryEnum.OUT, "-",
        Arrays.asList(MaterialDocSourceEnum.IMP, MaterialDocSourceEnum.INTERFACE)),
    RETURN_FROM_PURCHASE_CANCEL("RETURN_FROM_PURCHASE_CANCEL", "采购退供-撤销", MaterialAdjustTypeEnum.CG122, MaterialDocCategoryEnum.CANCEL, "+",
        Arrays.asList(MaterialDocSourceEnum.IMP, MaterialDocSourceEnum.INTERFACE)),

    INVENTORY_LOSS("INVENTORY_LOSS", "盘亏", MaterialAdjustTypeEnum.PK703, MaterialDocCategoryEnum.OUT, "-",
        Arrays.asList(MaterialDocSourceEnum.IMP, MaterialDocSourceEnum.INTERFACE)),
    INVENTORY_LOSS_CANCEL("INVENTORY_LOSS_CANCEL", "盘亏-撤销", MaterialAdjustTypeEnum.PK704, MaterialDocCategoryEnum.CANCEL, "+",
        Arrays.asList(MaterialDocSourceEnum.IMP, MaterialDocSourceEnum.INTERFACE)),

    SCRAPPED("SCRAPPED", "报废", MaterialAdjustTypeEnum.OBF705, MaterialDocCategoryEnum.OUT, "-",
        Arrays.asList(MaterialDocSourceEnum.IMP, MaterialDocSourceEnum.INTERFACE)),
    SCRAPPED_CANCEL("SCRAPPED_CANCEL", "报废-撤销", MaterialAdjustTypeEnum.OBF706, MaterialDocCategoryEnum.CANCEL, "+",
        Arrays.asList(MaterialDocSourceEnum.IMP, MaterialDocSourceEnum.INTERFACE)),

    INVENTORY_ADJUSTMENT_OUT("INVENTORY_ADJUSTMENT_OUT", "库存调整出库", MaterialAdjustTypeEnum.TZ711, MaterialDocCategoryEnum.OUT, "-",
        Arrays.asList(MaterialDocSourceEnum.INTERFACE)),
    INVENTORY_ADJUSTMENT_OUT_CANCEL("INVENTORY_ADJUSTMENT_OUT_CANCEL", "库存调整出库-撤销", MaterialAdjustTypeEnum.TZ712, MaterialDocCategoryEnum.CANCEL, "+",
        Arrays.asList(MaterialDocSourceEnum.INTERFACE)),

    TRANSFER_SAME_COMPANY_OUT("TRANSFER_SAME_COMPANY_OUT", "调拨出库（同一公司的不同仓库之间）", MaterialAdjustTypeEnum.DB405, MaterialDocCategoryEnum.OUT, "-",
        Arrays.asList(MaterialDocSourceEnum.INTERFACE)),
    TRANSFER_SAME_COMPANY_OUT_CANCEL("TRANSFER_SAME_COMPANY_OUT_CANCEL", "调拨出库-撤销（同一公司的不同仓库之间）", MaterialAdjustTypeEnum.DB406, MaterialDocCategoryEnum.CANCEL,
        "+", Arrays.asList(MaterialDocSourceEnum.INTERFACE)),

    TRANSFER_DIFFERENT_COMPANY_OUT("TRANSFER_DIFFERENT_COMPANY_OUT", "调拨出库（不同公司的不同仓库之间）", MaterialAdjustTypeEnum.DB401, MaterialDocCategoryEnum.OUT, "-",
        Arrays.asList(MaterialDocSourceEnum.INTERFACE)),
    TRANSFER_DIFFERENT_COMPANY_OUT_CANCEL("TRANSFER_DIFFERENT_COMPANY_OUT_CANCEL", "调拨出库-撤销（不同公司的不同仓库之间）", MaterialAdjustTypeEnum.DB402,
        MaterialDocCategoryEnum.CANCEL, "+", Arrays.asList(MaterialDocSourceEnum.INTERFACE)),

    /***出入库 + 冲销**/
    ALLOT_LOGICAL_PLANT_INNER("ALLOT_LOGICAL_PLANT_INNER", "调拨-逻辑仓库间", MaterialAdjustTypeEnum.ODB409, MaterialDocCategoryEnum.INOUT, "+-",
        Arrays.asList(MaterialDocSourceEnum.IMP, MaterialDocSourceEnum.INTERFACE)),
    ALLOT_LOGICAL_PLANT_INNER_CANCEL("ALLOT_LOGICAL_PLANT_INNER_CANCEL", "调拨-逻辑仓库间-撤销", MaterialAdjustTypeEnum.ODB410, MaterialDocCategoryEnum.CANCEL, "-+",
        Arrays.asList(MaterialDocSourceEnum.IMP, MaterialDocSourceEnum.INTERFACE)),

    ALLOT_LOGICAL_PLANT_OUTER("ALLOT_LOGICAL_PLANT_OUTER", "调拨-逻辑仓库内", MaterialAdjustTypeEnum.ODB411, MaterialDocCategoryEnum.INOUT, "+-",
        Arrays.asList(MaterialDocSourceEnum.IMP, MaterialDocSourceEnum.INTERFACE)),
    ALLOT_LOGICAL_PLANT_OUTER_CANCEL("ALLOT_LOGICAL_PLANT_OUTER_CANCEL", "调拨-逻辑仓库内-撤销", MaterialAdjustTypeEnum.ODB412, MaterialDocCategoryEnum.CANCEL, "-+",
        Arrays.asList(MaterialDocSourceEnum.IMP, MaterialDocSourceEnum.INTERFACE)),

    TRANSFER_UNRESTRICTED_INSPECTION("TRANSFER_UNRESTRICTED_INSPECTION", "库存转移-良品>质检", MaterialAdjustTypeEnum.ZY501, MaterialDocCategoryEnum.INOUT, "+-",
        Arrays.asList(MaterialDocSourceEnum.IMP, MaterialDocSourceEnum.INTERFACE)),

    TRANSFER_INSPECTION_UNRESTRICTED("TRANSFER_INSPECTION_UNRESTRICTED", "库存转移-质检>良品", MaterialAdjustTypeEnum.ZY502, MaterialDocCategoryEnum.INOUT, "+-",
        Arrays.asList(MaterialDocSourceEnum.IMP, MaterialDocSourceEnum.INTERFACE)),

    TRANSFER_INSPECTION_DAMAGED("TRANSFER_INSPECTION_DAMAGED", "库存转移-质检>残次", MaterialAdjustTypeEnum.ZY503, MaterialDocCategoryEnum.INOUT, "+-",
        Arrays.asList(MaterialDocSourceEnum.IMP, MaterialDocSourceEnum.INTERFACE)),

    TRANSFER_DAMAGED_INSPECTION("TRANSFER_DAMAGED_INSPECTION", "库存转移-残次>质检", MaterialAdjustTypeEnum.ZY504, MaterialDocCategoryEnum.INOUT, "+-",
        Arrays.asList(MaterialDocSourceEnum.IMP, MaterialDocSourceEnum.INTERFACE)),

    TRANSFER_UNRESTRICTED_DAMAGED("TRANSFER_UNRESTRICTED_DAMAGED", "库存转移-良品>残次", MaterialAdjustTypeEnum.ZY505, MaterialDocCategoryEnum.INOUT, "+-",
        Arrays.asList(MaterialDocSourceEnum.IMP, MaterialDocSourceEnum.INTERFACE)),

    TRANSFER_DAMAGED_UNRESTRICTED("TRANSFER_DAMAGED_UNRESTRICTED", "库存转移-残次>良品", MaterialAdjustTypeEnum.ZY506, MaterialDocCategoryEnum.INOUT, "+-",
        Arrays.asList(MaterialDocSourceEnum.IMP, MaterialDocSourceEnum.INTERFACE)),

    ;

    private String code;
    private String desc;
    private MaterialAdjustTypeEnum adjustTypeEnum;
    private MaterialDocCategoryEnum materialDocCategoryEnum;
    private String io;
    private List<MaterialDocSourceEnum> materialDocSourceEnums;

    public static BusinessTypeEnum businessTypeByAdjustType(String adjustType) {
        if (null == adjustType) {
            return null;
        }
        return Arrays.stream(values()).filter(e -> adjustType.equals(e.getAdjustTypeEnum().getCode())).findFirst().orElse(null);
    }

    public static MaterialDocCategoryEnum materialDocCategoryByAdjustType(String adjustType) {
        BusinessTypeEnum businessTypeEnum = businessTypeByAdjustType(adjustType);
        return Objects.nonNull(businessTypeEnum)?businessTypeEnum.getMaterialDocCategoryEnum():null;
    }

    public static String ioByCode(String code) {
        if (null == code) {
            return "";
        }
        return Arrays.stream(values()).filter(e -> code.equals(e.getAdjustTypeEnum().getCode())).map(BusinessTypeEnum::getIo).findFirst().orElse("");
    }

    public static boolean checkByCode(String code) {
        if (null == code) {
            return false;
        }
        BusinessTypeEnum typeEnum = Arrays.stream(values()).filter(e -> code.equals(e.getCode())).findFirst().orElse(null);
        return Objects.nonNull(typeEnum);

    }

    public static Map<MaterialDocCategoryEnum, List<BusinessTypeEnum>> getMaterialTypeMapping(MaterialDocSourceEnum materialDocSourceEnum) {
        return Stream.of(BusinessTypeEnum.values()).filter(e -> e.getMaterialDocSourceEnums().contains(materialDocSourceEnum))
            .collect(Collectors.groupingBy(BusinessTypeEnum::getMaterialDocCategoryEnum));
    }

    public static List<BusinessTypeEnum> getBusinessTypeByCategory(MaterialDocCategoryEnum materialDocCategoryEnum) {
        if (Objects.isNull(materialDocCategoryEnum)) {
            return Lists.newArrayList();
        }
        return Arrays.stream(values()).filter(d -> d.getMaterialDocCategoryEnum().equals(materialDocCategoryEnum)).collect(Collectors.toList());
    }
}
