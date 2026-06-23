package com.inventory.middle.domain.service.impl;

import com.inventory.middle.domain.model.bo.inventory.InventorySnapshotBO;
import com.inventory.middle.domain.model.bo.inventorysnapshot.MonitorInventorySnapshotPageBO;
import com.inventory.middle.domain.model.bo.monitor.InventoryAlertBO;
import com.inventory.middle.domain.model.entity.InventoryAlert;
import com.inventory.middle.domain.model.entity.InventorySnapshot;
import com.inventory.middle.domain.model.enums.StockTypeEnum;
import com.inventory.middle.domain.model.types.InventorySnapshotId;
import com.inventory.middle.domain.repository.InventoryAlertRepository;
import com.inventory.middle.domain.repository.InventorySnapshotRepository;
import com.inventory.middle.domain.service.InventorySnapshotDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 库存快照服务实现
 * 迁移自: com.enn.inventory.center.biz.service.impl.InventorySnapshotBizServiceImpl
 */
@Slf4j
@Service
public class InventorySnapshotCoreService implements InventorySnapshotDomainService {

    @Resource
    private InventorySnapshotRepository inventorySnapshotRepository;

    @Resource
    private InventoryAlertRepository inventoryAlertRepository;

    // ===== implements InventorySnapshotDomainService =====

    @Override
    public List<InventorySnapshotBO> pageMaterialTotal(MonitorInventorySnapshotPageBO reqBO) {
        Map<String, Object> params = buildParams(reqBO);
        List<InventorySnapshot> list = inventorySnapshotRepository.queryMaterialTotal(params);
        return toBoList(list);
    }

    @Override
    public List<InventorySnapshotBO> queryByMaterialAndLogical(MonitorInventorySnapshotPageBO reqBO) {
        Map<String, Object> params = buildParams(reqBO);
        List<InventorySnapshot> list = inventorySnapshotRepository.queryByMaterialAndLogical(params);
        return toBoList(list);
    }

    @Override
    public List<InventorySnapshotBO> getSnapshotByMonitor(String tenantId, List<String> monitorObjects, String dimension) {
        Map<String, Object> params = new HashMap<>();
        params.put("tenantId", tenantId);
        params.put("dimension", dimension);
        if (!CollectionUtils.isEmpty(monitorObjects)) {
            params.put("monitorObjects", monitorObjects);
        }
        List<InventorySnapshot> list = inventorySnapshotRepository.queryList(params);
        return toBoList(list);
    }

    // ===== 内部方法（被 Handle 链调用）=====

    public List<InventorySnapshotBO> allAvailableSortList(String materialCode, String logicalPlantNo,
            StockTypeEnum stockTypeEnum, String tenantId, String storageLocationNo) {
        Map<String, Object> params = new HashMap<>();
        params.put("materialCode", materialCode);
        params.put("logicalPlantNo", logicalPlantNo);
        params.put("tenantId", tenantId);
        if (storageLocationNo != null) {
            params.put("storageLocationNo", storageLocationNo);
        }
        if (stockTypeEnum != null) {
            params.put("stockType", stockTypeEnum.getCode());
        }
        List<InventorySnapshot> list = inventorySnapshotRepository.queryList(params);
        return toBoList(list);
    }

    public void disableById(Long id, BigDecimal number, StockTypeEnum stockTypeEnum, boolean delFlag) {
        if (id == null) {
            log.warn("InventorySnapshotCoreService.disableById called with null id, skip");
            return;
        }
        inventorySnapshotRepository.disableById(id);
    }

    public void adjustDown(String materialCode, String batchNo, BigDecimal number,
            StockTypeEnum stockTypeEnum, String tenantId, boolean deletedSnp) {
        Map<String, Object> params = new HashMap<>();
        params.put("materialCode", materialCode);
        params.put("batchNo", batchNo);
        params.put("tenantId", tenantId);
        List<InventorySnapshot> list = inventorySnapshotRepository.queryList(params);
        if (CollectionUtils.isEmpty(list)) {
            log.warn("InventorySnapshotCoreService.adjustDown no snapshot found, materialCode={} batchNo={}", materialCode, batchNo);
            return;
        }
        InventorySnapshot snap = list.get(0);
        Integer stockTypeCode = stockTypeEnum != null ? stockTypeEnum.getCode() : StockTypeEnum.UNRESTRICTED.getCode();
        inventorySnapshotRepository.adjustDown(snap.getId().get(), number, stockTypeCode);
        if (deletedSnp) {
            inventorySnapshotRepository.disableById(snap.getId().get());
        }
    }

    public BigDecimal countAvailable(String skuCode, String logicPlantNo, StockTypeEnum stockTypeEnum,
            String tenantId, String batchNo, String locationNo) {
        Map<String, Object> params = new HashMap<>();
        params.put("materialCode", skuCode);
        params.put("logicalPlantNo", logicPlantNo);
        params.put("tenantId", tenantId);
        if (batchNo != null) {
            params.put("batchNo", batchNo);
        }
        if (locationNo != null) {
            params.put("storageLocationNo", locationNo);
        }
        List<InventorySnapshot> list = inventorySnapshotRepository.queryList(params);
        if (CollectionUtils.isEmpty(list)) {
            return BigDecimal.ZERO;
        }
        return list.stream()
                .map(e -> e.getUnrestricted() == null ? BigDecimal.ZERO : e.getUnrestricted())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public InventorySnapshotBO getById(Long id) {
        if (id == null) {
            return null;
        }
        InventorySnapshot entity = inventorySnapshotRepository.findById(new InventorySnapshotId(id));
        return toBO(entity);
    }

    public void saveAlertInfo(List<InventoryAlertBO> alertList) {
        if (CollectionUtils.isEmpty(alertList)) {
            return;
        }
        List<InventoryAlert> entities = alertList.stream().map(bo -> {
            InventoryAlert e = new InventoryAlert();
            BeanUtils.copyProperties(bo, e);
            return e;
        }).collect(Collectors.toList());
        inventoryAlertRepository.batchStore(entities);
    }

    public void deleteAlertId(List<InventoryAlertBO> alertList) {
        if (CollectionUtils.isEmpty(alertList)) {
            return;
        }
        List<Long> ids = alertList.stream()
                .filter(bo -> bo.getId() != null)
                .map(InventoryAlertBO::getId)
                .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(ids)) {
            inventoryAlertRepository.batchDeleteByIds(ids);
        }
    }

    // ===== 私有转换方法 =====

    private Map<String, Object> buildParams(MonitorInventorySnapshotPageBO reqBO) {
        Map<String, Object> params = new HashMap<>();
        if (reqBO == null) {
            return params;
        }
        if (reqBO.getTenantId() != null) {
            params.put("tenantId", reqBO.getTenantId());
        }
        if (!CollectionUtils.isEmpty(reqBO.getMaterialCodeList())) {
            params.put("materialCodeList", reqBO.getMaterialCodeList());
        }
        if (!CollectionUtils.isEmpty(reqBO.getLogicalPlantIdList())) {
            params.put("logicalPlantIdList", reqBO.getLogicalPlantIdList());
        }
        return params;
    }

    private InventorySnapshotBO toBO(InventorySnapshot entity) {
        if (entity == null) {
            return null;
        }
        InventorySnapshotBO bo = new InventorySnapshotBO();
        BeanUtils.copyProperties(entity, bo);
        if (entity.getId() != null) {
            bo.setId(entity.getId().get());
        }
        return bo;
    }

    private List<InventorySnapshotBO> toBoList(List<InventorySnapshot> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        return list.stream().map(this::toBO).collect(Collectors.toList());
    }
}
