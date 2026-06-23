package com.inventory.middle.domain.service;

import com.inventory.middle.domain.model.bo.inventory.InventorySnapshotBO;
import com.inventory.middle.domain.model.bo.monitor.InventoryMonitorRuleBO;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/** 库存预警业务服务接口（占位，C2 实现） */
public interface InventoryAlertDomainService {
    boolean createAlertAndNotification(List<InventorySnapshotBO> alertMaterial,
        InventoryMonitorRuleBO ruleBO, Map<String, BigDecimal> alertKeyDeviateMap);
    boolean deleteAlertAndNotification(InventoryMonitorRuleBO ruleBO, List<Long> alertIds);
    boolean deleteAlertByRule(InventoryMonitorRuleBO ruleBO);
}
