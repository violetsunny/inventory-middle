package com.inventory.middle.application.bo.monitor;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import top.kdla.framework.supplement.excel.BaseExcel;

import java.math.BigDecimal;

/**
 * 库存预警规则明细 Excel 导入/模板 BO
 */
@Data
public class ImportMonitorRuleLineExcelBO extends BaseExcel {

    @ExcelProperty("预警规则ID")
    private Long monitorRuleId;

    @ExcelProperty("预警维度")
    private String monitorDimension;

    @ExcelProperty("预警对象")
    private String monitorObject;

    @ExcelProperty("上限")
    private BigDecimal monitorCeil;

    @ExcelProperty("下限")
    private BigDecimal monitorFloor;

    @ExcelProperty("租户ID")
    private String tenantId;
}
