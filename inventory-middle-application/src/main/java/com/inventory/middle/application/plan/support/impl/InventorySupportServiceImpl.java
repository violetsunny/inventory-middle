package com.inventory.middle.application.plan.support.impl;

import com.inventory.middle.application.plan.support.InventorySupportService;
import com.inventory.middle.application.service.InventoryDemandQueryService;
import com.inventory.middle.application.service.InventoryMaterialApplicationService;
import com.inventory.middle.application.service.InventorySnapshotQueryService;
import com.inventory.middle.application.service.InventorySupplyQueryService;
import com.inventory.middle.application.service.LogicalPlantQueryService;
import com.inventory.middle.client.dto.LogicalPlantDto;
import com.inventory.middle.client.plan.dto.inventory.InvPlantBO;
import com.inventory.middle.client.plan.dto.inventory.request.MaterialPlanInventoryRequest;
import com.inventory.middle.domain.model.bo.inventory.InventoryDemandByDayQueryBO;
import com.inventory.middle.domain.model.bo.inventory.InventorySupplyByDayQueryBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class InventorySupportServiceImpl implements InventorySupportService {

    @Resource
    private LogicalPlantQueryService logicalPlantQueryService;

    @Resource
    private InventorySnapshotQueryService inventorySnapshotQueryService;

    @Resource
    private InventorySupplyQueryService inventorySupplyQueryService;

    @Resource
    private InventoryDemandQueryService inventoryDemandQueryService;

    @Resource
    private InventoryMaterialApplicationService inventoryMaterialApplicationService;

    @Override
    public List<InvPlantBO> listPlants(String tenant) {
        return logicalPlantQueryService.list(tenant);
    }

    @Override
    public InvPlantBO findPlant(String plantCode, String tenant) {
        LogicalPlantDto dto = logicalPlantQueryService.findByNo(plantCode);
        if (dto == null) {
            log.warn("findPlant: logicalPlant not found, plantCode={}, tenant={}", plantCode, tenant);
            return null;
        }
        InvPlantBO bo = new InvPlantBO();
        bo.setPlantId(dto.getId());
        bo.setPlantCode(dto.getLogicalPlantNo());
        bo.setPlantName(dto.getLogicalPlantName());
        bo.setTenantId(dto.getTenantId());
        return bo;
    }

    @Override
    public BigDecimal queryInventory(String materialCode, String plantCode, String tenant) {
        return inventorySnapshotQueryService.queryCurrentInventoryByCondition(materialCode, plantCode, tenant);
    }

    @Override
    public BigDecimal queryOverduePlanInventory(MaterialPlanInventoryRequest request) {
        Long logicalPlantId = resolveLogicalPlantId(request.getPlantCode());
        if (logicalPlantId == null) {
            return BigDecimal.ZERO;
        }
        if (request.isPlanSupply()) {
            InventorySupplyByDayQueryBO query = new InventorySupplyByDayQueryBO();
            query.setTenantId(request.getTenant());
            query.setMaterialCode(request.getMaterialCode());
            query.setLogicalPlantId(logicalPlantId);
            query.setStartTime(toStartOfDay(request.getStartTime()));
            query.setEndTime(toEndOfDay(request.getEndTime()));
            return inventorySupplyQueryService.queryOverdueSupplyTotal(query);
        } else if (request.isPlanDemand()) {
            InventoryDemandByDayQueryBO query = new InventoryDemandByDayQueryBO();
            query.setTenantId(request.getTenant());
            query.setMaterialCode(request.getMaterialCode());
            query.setLogicalPlantId(logicalPlantId);
            query.setStartTime(toStartOfDay(request.getStartTime()));
            query.setEndTime(toEndOfDay(request.getEndTime()));
            return inventoryDemandQueryService.queryOverdueDemandTotal(query);
        }
        log.warn("queryOverduePlanInventory: unknown plan type, request={}", request);
        return BigDecimal.ZERO;
    }

    @Override
    public Map<LocalDate, BigDecimal> queryPlanInventory(MaterialPlanInventoryRequest request) {
        Long logicalPlantId = resolveLogicalPlantId(request.getPlantCode());
        if (logicalPlantId == null) {
            return Collections.emptyMap();
        }
        if (request.isPlanSupply()) {
            InventorySupplyByDayQueryBO query = new InventorySupplyByDayQueryBO();
            query.setTenantId(request.getTenant());
            query.setMaterialCode(request.getMaterialCode());
            query.setLogicalPlantId(logicalPlantId);
            query.setStartTime(toStartOfDay(request.getStartTime()));
            query.setEndTime(toEndOfDay(request.getEndTime()));
            return inventorySupplyQueryService.querySupplyByDay(query);
        } else if (request.isPlanDemand()) {
            InventoryDemandByDayQueryBO query = new InventoryDemandByDayQueryBO();
            query.setTenantId(request.getTenant());
            query.setMaterialCode(request.getMaterialCode());
            query.setLogicalPlantId(logicalPlantId);
            query.setStartTime(toStartOfDay(request.getStartTime()));
            query.setEndTime(toEndOfDay(request.getEndTime()));
            return inventoryDemandQueryService.queryDemandByDay(query);
        }
        log.warn("queryPlanInventory: unknown plan type, request={}", request);
        return Collections.emptyMap();
    }

    @Override
    public Map<String, List<String>> queryMaterialsByPlantCodes(String tenant, List<String> plantCodes) {
        return inventoryMaterialApplicationService.listByLogicalPlantNos(plantCodes, tenant);
    }

    private Long resolveLogicalPlantId(String plantCode) {
        LogicalPlantDto dto = logicalPlantQueryService.findByNo(plantCode);
        if (dto == null) {
            log.warn("resolveLogicalPlantId: logicalPlant not found, plantCode={}", plantCode);
            return null;
        }
        return dto.getId();
    }

    private LocalDateTime toStartOfDay(LocalDate date) {
        return date == null ? null : date.atStartOfDay();
    }

    private LocalDateTime toEndOfDay(LocalDate date) {
        return date == null ? null : date.atTime(LocalTime.MAX);
    }
}
