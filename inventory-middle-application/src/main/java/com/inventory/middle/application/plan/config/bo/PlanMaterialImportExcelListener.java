package com.inventory.middle.application.plan.config.bo;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * EasyExcel listener for 计划物料清单 import.
 * Parses each row from PlanMaterialImportExcelBO into PlanMaterialBO.
 */
@Slf4j
@Getter
public class PlanMaterialImportExcelListener extends AnalysisEventListener<PlanMaterialImportExcelBO> {

    /** Rows that passed validation. */
    private final List<PlanMaterialBO> successList = new ArrayList<>();

    /** Rows that failed validation. */
    private final List<String> failedReasons = new ArrayList<>();

    private final String tenantId;
    private final String userId;

    public PlanMaterialImportExcelListener(String tenantId, String userId) {
        this.tenantId = tenantId;
        this.userId = userId;
    }

    @Override
    public void invoke(PlanMaterialImportExcelBO bo, AnalysisContext context) {
        int rowIndex = context.readRowHolder().getRowIndex() + 1;
        try {
            if (StringUtils.isBlank(bo.getMaterialCode())) {
                failedReasons.add("第" + rowIndex + "行：物料编码不能为空");
                return;
            }
            if (StringUtils.isBlank(bo.getLogicalPlantNo())) {
                failedReasons.add("第" + rowIndex + "行：逻辑仓编码不能为空");
                return;
            }

            PlanMaterialBO materialBO = new PlanMaterialBO();
            materialBO.setMaterialCode(bo.getMaterialCode().trim());
            materialBO.setLogicalPlantNo(bo.getLogicalPlantNo().trim());
            materialBO.setTenantId(tenantId);
            materialBO.setOperatorId(userId);
            materialBO.setIndex(rowIndex);

            successList.add(materialBO);
        } catch (Exception e) {
            log.warn("PlanMaterialImportExcelListener parse error at row {}", rowIndex, e);
            failedReasons.add("第" + rowIndex + "行：解析异常 - " + e.getMessage());
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("PlanMaterialImportExcelListener finished: success={}, failed={}",
                successList.size(), failedReasons.size());
    }
}
