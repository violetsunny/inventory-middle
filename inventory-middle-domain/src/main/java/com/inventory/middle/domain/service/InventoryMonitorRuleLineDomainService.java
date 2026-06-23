package com.inventory.middle.domain.service;

import com.inventory.middle.client.dto.monitory.FailedCreateMonitorRuleLineDTO;
import com.inventory.middle.client.dto.monitory.InventoryMonitorRuleLineInfoDTO;
import com.inventory.middle.domain.model.bo.monitor.InventoryMonitorRuleLineBO;
import com.inventory.middle.domain.model.bo.monitor.InventoryMonitorRuleLinePageQuery;
import com.inventory.middle.domain.model.bo.monitor.UpdateMonitorRuleLineReq;
import top.kdla.framework.dto.PageResponse;

import java.util.List;

/** 库存监控规则行业务服务接口 */
public interface InventoryMonitorRuleLineDomainService {
    List<InventoryMonitorRuleLineBO> queryByMonitorRuleIds(List<Long> monitorRuleIds);
    PageResponse<InventoryMonitorRuleLineBO> pageRuleLineList(InventoryMonitorRuleLinePageQuery pageQuery);
    boolean batchCreate(List<InventoryMonitorRuleLineBO> list);
    boolean updateRuleLine(UpdateMonitorRuleLineReq request);
    List<InventoryMonitorRuleLineBO> queryByIds(List<Long> ruleLineIds);
    <T extends InventoryMonitorRuleLineInfoDTO> List<FailedCreateMonitorRuleLineDTO> validateCreateMaterial(List<T> infoDTOS, Boolean enableStatus);
    List<InventoryMonitorRuleLineBO> queryByMonitorObjects(String tenantId, String monitorType, List<String> monitorObjects, String monitorDimension);
}
