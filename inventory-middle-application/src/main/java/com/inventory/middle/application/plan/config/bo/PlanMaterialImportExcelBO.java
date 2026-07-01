package com.inventory.middle.application.plan.config.bo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PlanMaterialImportExcelBO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "物料编码", index = 0)
    private String materialCode;

    @ExcelProperty(value = "逻辑仓编码", index = 1)
    private String logicalPlantNo;

    @ExcelProperty(value = "物料描述", index = 2)
    private String materialDesc;
}
