package com.inventory.middle.application.plan.dss;

import com.inventory.middle.application.plan.support.ProductSupportService;
import com.inventory.middle.client.plan.dto.product.ProductBO;
import com.inventory.middle.infra.plan.persistence.dao.PlanDemandSupplyStockDao;
import com.inventory.middle.infra.plan.persistence.entity.PlanDemandSupplyStockPO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PlanDemandSupplyStockApplicationServiceImpl implements PlanDemandSupplyStockApplicationService {

    @Resource
    private PlanDemandSupplyStockDao planDemandSupplyStockDao;

    @Resource
    private ProductSupportService productSupportService;

    @Value("${plan.demand.supply.stock.after.days:5}")
    private int afterDays;

    @Override
    public Map<String, Object> demandSupplyStockBoard(
            String logicalPlantNo, String materialCode, String tenantId, int pageNum, int pageSize) {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> rows = new ArrayList<>();
        long total = 0;
        try {
            List<String> materialCodes = Collections.singletonList(materialCode);
            List<PlanDemandSupplyStockPO> stockRecords = planDemandSupplyStockDao.batchQueryByMaterialCode(
                    tenantId, logicalPlantNo, materialCodes);
            if (CollectionUtils.isNotEmpty(stockRecords)) {
                Map<String, PlanDemandSupplyStockPO> materialMap = new LinkedHashMap<>();
                for (PlanDemandSupplyStockPO po : stockRecords) {
                    materialMap.putIfAbsent(po.getMaterialCode(), po);
                }
                Map<String, ProductBO> productMap = productSupportService.queryProductMap(
                        new ArrayList<>(materialMap.keySet()), tenantId);
                for (Map.Entry<String, PlanDemandSupplyStockPO> entry : materialMap.entrySet()) {
                    Map<String, Object> row = new HashMap<>();
                    row.put("materialCode", entry.getKey());
                    row.put("logicalPlantNo", logicalPlantNo);
                    ProductBO product = productMap.get(entry.getKey());
                    if (product != null) {
                        row.put("materialDesc", product.getName());
                        row.put("materialUnit", product.getUnit());
                    }
                    rows.add(row);
                }
            }
            total = rows.size();
            int start = (pageNum - 1) * pageSize;
            int end = Math.min(start + pageSize, rows.size());
            rows = start < rows.size() ? rows.subList(start, end) : Collections.emptyList();
        } catch (Exception e) {
            log.error("demandSupplyStockBoard error", e);
        }
        result.put("list", rows);
        result.put("total", total);
        return result;
    }

    @Override
    public Map<String, Object> demandSupplyStockBoardDetail(
            String materialCode, String logicalPlantNo, String tenantId) {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> boardData = new ArrayList<>();
        List<Map<String, Object>> chartData = new ArrayList<>();
        try {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, -30);
            Date startDate = cal.getTime();
            cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, afterDays);
            Date endDate = cal.getTime();

            List<PlanDemandSupplyStockPO> records = planDemandSupplyStockDao.queryByPlanDate(
                    tenantId, logicalPlantNo, materialCode, startDate, endDate);
            if (CollectionUtils.isNotEmpty(records)) {
                records.sort(Comparator.comparing(PlanDemandSupplyStockPO::getPlanDate)
                        .thenComparing(PlanDemandSupplyStockPO::getPlanElementType));
                long availableAmount = 0;
                for (PlanDemandSupplyStockPO po : records) {
                    Map<String, Object> dataRow = new HashMap<>();
                    dataRow.put("planDate", po.getPlanDate());
                    dataRow.put("planElementType", po.getPlanElementType());
                    dataRow.put("voucherNo", po.getVoucherNo());
                    dataRow.put("documentNo", po.getDocumentNo());
                    dataRow.put("exceptionCode", po.getExceptionCode());
                    long amount = po.getAmount() != null ? po.getAmount() : 0;
                    if (po.getBizType() != null && po.getBizType() == 1) {
                        dataRow.put("demandAmount", amount);
                        dataRow.put("supplyAmount", 0L);
                        availableAmount -= amount;
                    } else {
                        dataRow.put("demandAmount", 0L);
                        dataRow.put("supplyAmount", amount);
                        availableAmount += amount;
                    }
                    dataRow.put("availableAmount", availableAmount);
                    boardData.add(dataRow);
                }

                Map<Date, List<PlanDemandSupplyStockPO>> dateGrouped = records.stream()
                        .collect(Collectors.groupingBy(PlanDemandSupplyStockPO::getPlanDate,
                                LinkedHashMap::new, Collectors.toList()));
                for (Map.Entry<Date, List<PlanDemandSupplyStockPO>> entry : dateGrouped.entrySet()) {
                    long demand = entry.getValue().stream()
                            .filter(p -> p.getBizType() != null && p.getBizType() == 1)
                            .mapToLong(p -> p.getAmount() != null ? p.getAmount() : 0).sum();
                    long supply = entry.getValue().stream()
                            .filter(p -> p.getBizType() != null && p.getBizType() == 2)
                            .mapToLong(p -> p.getAmount() != null ? p.getAmount() : 0).sum();
                    Map<String, Object> chartRow = new HashMap<>();
                    chartRow.put("demandAmount", demand);
                    chartRow.put("supplyAmount", supply);
                    chartData.add(chartRow);
                }
            }
        } catch (Exception e) {
            log.error("demandSupplyStockBoardDetail error", e);
        }
        result.put("boardData", boardData);
        result.put("chartData", chartData);
        return result;
    }

    @Override
    public Map<String, Object> renderBomTree(String materialCode, String logicalPlantNo, String tenantId) {
        Map<String, Object> result = new HashMap<>();
        result.put("materialCode", materialCode);
        ProductBO product = productSupportService.findProduct(materialCode, tenantId);
        if (product != null) {
            result.put("materialDesc", product.getName());
        }
        result.put("children", Collections.emptyList());
        return result;
    }

    @Override
    public void generateData(String materialCode, String logicalPlantNo, String tenantId) {
        log.info("generateData materialCode={}, logicalPlantNo={}, tenantId={}", materialCode, logicalPlantNo, tenantId);
    }
}
