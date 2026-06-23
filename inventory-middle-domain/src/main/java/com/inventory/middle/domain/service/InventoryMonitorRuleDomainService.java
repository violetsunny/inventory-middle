package com.inventory.middle.domain.service;

import com.inventory.middle.domain.model.bo.monitor.InventoryMonitorRuleBO;
import com.inventory.middle.domain.model.bo.monitor.InventoryMonitorRulePageQuery;
import com.inventory.middle.domain.model.bo.monitor.UpdateMonitorRuleLineReq;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.SingleResponse;

import java.util.List;

/** 库存预警规则业务服务接口 */
public interface InventoryMonitorRuleDomainService {
    SingleResponse<Long> create(InventoryMonitorRuleBO ruleBO);
    SingleResponse<Boolean> delete(InventoryMonitorRuleBO monitorRuleBO);
    SingleResponse<Boolean> update(InventoryMonitorRuleBO ruleBO);
    SingleResponse<Boolean> updateRuleAndLine(InventoryMonitorRuleBO ruleRequest, UpdateMonitorRuleLineReq lineRequest);
    InventoryMonitorRuleBO queryById(Long id);
    PageResponse<InventoryMonitorRuleBO> pageList(InventoryMonitorRulePageQuery pageQuery);
    PageResponse<InventoryMonitorRuleBO> pageListFromMaxId(InventoryMonitorRulePageQuery pageQuery, Long ruleId);
    List<InventoryMonitorRuleBO> queryByMonitorObjects(String tenantId, List<String> monitorObjects, String monitorDimension);
}