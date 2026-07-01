package com.inventory.middle.application.plan.config.bo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PlanMaterialParamImportExcelBO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "物料编码", index = 0)
    private String materialCode;

    @ExcelProperty(value = "逻辑仓编码", index = 1)
    private String logicalPlantNo;

    @ExcelProperty(value = "计划类型(采购/调拨/生产)", index = 2)
    private String planType;

    @ExcelProperty(value = "提前期（天）", index = 3)
    private String vendorLeadTime;

    @ExcelProperty(value = "需求时区（天）", index = 4)
    private String demandTimeFence;

    @ExcelProperty(value = "计划时区（天）", index = 5)
    private String planningTimeFence;

    @ExcelProperty(value = "订货批量", index = 6)
    private String orderQuantity;

    @ExcelProperty(value = "最小订货批量", index = 7)
    private String minOrderQuantity;

    @ExcelProperty(value = "订货周期（天）", index = 8)
    private String orderCycleTime;

    @ExcelProperty(value = "安全库存计算方式(0=不设置/1=设置安全库存/2=公式计算)", index = 9)
    private String safetyStockCalType;

    @ExcelProperty(value = "安全库存(安全库存计算=1设置)", index = 10)
    private String safetyStock;

    @ExcelProperty(value = "保障间隔内的需求标准差(安全库存计算=2设置)", index = 11)
    private String demandStd;

    @ExcelProperty(value = "保障间隔(安全库存计算=2设置)", index = 12)
    private String guaranteeInterval;

    @ExcelProperty(value = "安全系数(安全库存计算=2设置)", index = 13)
    private String safetyCoefficient;

    @ExcelProperty(value = "损耗率（%）", index = 14)
    private String lossRate;

    @ExcelProperty(value = "成品率（%）", index = 15)
    private String finishedRate;

    @ExcelProperty(value = "调拨源头逻辑仓", index = 16)
    private String transferLogicalPlantNo;

    @ExcelProperty(value = "需求策略编码", index = 17)
    private String demandStrategyCode;
}
