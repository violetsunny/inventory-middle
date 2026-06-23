package com.inventory.middle.application.service.monitor;

import com.inventory.middle.application.bo.monitor.ImportMonitorRuleLineExcelBO;
import top.kdla.framework.supplement.excel.imp.KdlaExcelReadListener;

import java.util.List;
import java.util.function.Predicate;

/**
 * 库存预警规则明细 Excel 导入监听器
 * <p>
 * T = 原始行数据 ImportMonitorRuleLineExcelBO
 * E = 行级校验通过后的结果（复用同一类型）
 */
public class MonitorRuleLineExcelReadListener
        extends KdlaExcelReadListener<ImportMonitorRuleLineExcelBO, ImportMonitorRuleLineExcelBO> {

    public MonitorRuleLineExcelReadListener(Predicate<List<ImportMonitorRuleLineExcelBO>> predicate) {
        super(predicate);
    }

    /**
     * 行级校验：预警维度和预警对象必填，否则跳过该行
     */
    @Override
    public ImportMonitorRuleLineExcelBO checkData(ImportMonitorRuleLineExcelBO row) {
        if (row.getMonitorDimension() == null || row.getMonitorObject() == null) {
            return null;
        }
        return row;
    }
}
