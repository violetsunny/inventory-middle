package com.inventory.middle.domain.service;

import com.inventory.middle.domain.model.bo.inventory.InventorySnapshotBO;
import com.inventory.middle.domain.model.bo.inventorysnapshot.MonitorInventorySnapshotPageBO;
import java.util.List;

/** 库存快照业务服务接口（占位，C2 实现） */
public interface InventorySnapshotDomainService {
    List<InventorySnapshotBO> pageMaterialTotal(MonitorInventorySnapshotPageBO reqBO);
    List<InventorySnapshotBO> queryByMaterialAndLogical(MonitorInventorySnapshotPageBO reqBO);
    List<InventorySnapshotBO> getSnapshotByMonitor(String tenantId, List<String> monitorObjects, String dimension);
}
