package com.inventory.middle.application.plan.dss;

import java.util.List;
import java.util.Map;

public interface PlanDemandSupplyStockApplicationService {

    Map<String, Object> demandSupplyStockBoard(
            String logicalPlantNo, String materialCode, String tenantId, int pageNum, int pageSize);

    Map<String, Object> demandSupplyStockBoardDetail(
            String materialCode, String logicalPlantNo, String tenantId);

    Map<String, Object> renderBomTree(
            String materialCode, String logicalPlantNo, String tenantId);

    void generateData(String materialCode, String logicalPlantNo, String tenantId);
}
