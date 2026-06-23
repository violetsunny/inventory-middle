package com.inventory.middle.domain.service;

import com.inventory.middle.client.dto.PageRequest;
import com.inventory.middle.domain.model.bo.monitor.InventoryMonitorRuleBO;
import com.inventory.middle.domain.model.bo.monitor.InventoryMonitorRulePageQuery;
import top.kdla.framework.dto.PageResponse;

import java.util.List;

/**
 * @description 库存预警规则
 * @author dongguo.tao
 * @date 2021-06-16
 */
public interface InventoryMonitorRuleCoreService {

    /**
     * 新增
     */
    Long create(InventoryMonitorRuleBO monitorRuleBO);

    /**
     * 更新
     */
    boolean update(InventoryMonitorRuleBO monitorRuleBO);

    /**
     * 根据主键 id 查询
     */
    InventoryMonitorRuleBO load(Long id);

    /**
     * 不带状态的查询状态的规则
     * @param id
     * @return
     */
    InventoryMonitorRuleBO queryByIdWithoutStatus(Long id);


    /**
     * 根据逻辑仓id集合查询预警规则
     * @param monitorObjects
     * @param monitorDimension
     * @param tenantId
     * @param enableStatus
     * @return
     */
    List<InventoryMonitorRuleBO> queryByMonitorObjects(List<String> monitorObjects, String monitorDimension,
                                                       String tenantId, String enableStatus);

    /**
     * 分页查询
     */
    PageResponse<InventoryMonitorRuleBO> pageList(InventoryMonitorRulePageQuery pageQuery);

    /**
     * 根据id批量查询
     *
     * @param pageQuery
     * @param ruleId
     * @return
     */
    PageResponse<InventoryMonitorRuleBO> pageListFromMaxId(InventoryMonitorRulePageQuery pageQuery, Long ruleId);

}
