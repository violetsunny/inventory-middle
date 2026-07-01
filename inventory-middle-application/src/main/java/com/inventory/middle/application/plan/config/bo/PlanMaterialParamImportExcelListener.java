package com.inventory.middle.application.plan.config.bo;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * EasyExcel listener for 计划物料参数 import.
 * Parses each row from PlanMaterialParamImportExcelBO into PlanMaterialParameterBO.
 */
@Slf4j
@Getter
public class PlanMaterialParamImportExcelListener extends AnalysisEventListener<PlanMaterialParamImportExcelBO> {

    /** Rows that passed validation. */
    private final List<PlanMaterialParameterBO> successList = new ArrayList<>();

    /** Rows that failed validation: [(rowIndex, reason)]. */
    private final List<String> failedReasons = new ArrayList<>();

    private final String tenantId;
    private final String userId;
    private final String userName;

    public PlanMaterialParamImportExcelListener(String tenantId, String userId, String userName) {
        this.tenantId = tenantId;
        this.userId = userId;
        this.userName = userName;
    }

    @Override
    public void invoke(PlanMaterialParamImportExcelBO bo, AnalysisContext context) {
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

            PlanMaterialParameterBO paramBO = new PlanMaterialParameterBO();
            paramBO.setMaterialCode(bo.getMaterialCode().trim());
            paramBO.setLogicalPlantNo(bo.getLogicalPlantNo().trim());
            paramBO.setDemandStrategyCode(bo.getDemandStrategyCode());
            paramBO.setTransferLogicalPlantNo(bo.getTransferLogicalPlantNo());
            paramBO.setTenantId(tenantId);
            paramBO.setUserId(userId);
            paramBO.setUserName(userName);
            paramBO.setIndex(rowIndex);

            if (NumberUtils.isParsable(bo.getVendorLeadTime())) {
                paramBO.setVendorLeadTime(Integer.parseInt(bo.getVendorLeadTime()));
            }
            if (NumberUtils.isParsable(bo.getPlanningTimeFence())) {
                paramBO.setPlanningTimeFence(Integer.parseInt(bo.getPlanningTimeFence()));
            }
            if (NumberUtils.isParsable(bo.getDemandTimeFence())) {
                paramBO.setDemandTimeFence(Integer.parseInt(bo.getDemandTimeFence()));
            }
            if (NumberUtils.isParsable(bo.getOrderQuantity())) {
                paramBO.setOrderQuantity(Long.parseLong(bo.getOrderQuantity()));
            }
            if (NumberUtils.isParsable(bo.getMinOrderQuantity())) {
                paramBO.setMinOrderQuantity(Long.parseLong(bo.getMinOrderQuantity()));
            }
            if (NumberUtils.isParsable(bo.getOrderCycleTime())) {
                paramBO.setOrderCycleTime(Integer.parseInt(bo.getOrderCycleTime()));
            }
            if (NumberUtils.isParsable(bo.getSafetyStockCalType())) {
                paramBO.setSafetyStockCalType(Integer.parseInt(bo.getSafetyStockCalType()));
            }
            if (NumberUtils.isParsable(bo.getSafetyStock())) {
                paramBO.setSafetyStock(Long.parseLong(bo.getSafetyStock()));
            }
            if (NumberUtils.isParsable(bo.getLossRate())) {
                paramBO.setLossRate(Integer.parseInt(bo.getLossRate()));
            }
            if (NumberUtils.isParsable(bo.getFinishedRate())) {
                paramBO.setFinishedRate(Integer.parseInt(bo.getFinishedRate()));
            }

            successList.add(paramBO);
        } catch (Exception e) {
            log.warn("PlanMaterialParamImportExcelListener parse error at row {}", rowIndex, e);
            failedReasons.add("第" + rowIndex + "行：解析异常 - " + e.getMessage());
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("PlanMaterialParamImportExcelListener finished: success={}, failed={}",
                successList.size(), failedReasons.size());
    }
}
