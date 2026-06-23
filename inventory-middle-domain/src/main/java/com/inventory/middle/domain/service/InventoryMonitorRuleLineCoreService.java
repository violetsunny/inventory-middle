package com.inventory.middle.domain.service;

import com.inventory.middle.domain.model.bo.monitor.InventoryMonitorRuleLineBO;
import com.inventory.middle.domain.model.bo.monitor.InventoryMonitorRuleLinePageQuery;
import top.kdla.framework.dto.PageResponse;

import java.util.List;

/**
 * @description 库存预警规则明细
 * @author dongguo.tao
 * @date 2021-06-16
 */
public interface InventoryMonitorRuleLineCoreService {

    /**
     * 新增
     */
    Long create(InventoryMonitorRuleLineBO inventoryMonitorRuleLine);

    /**
     * 批量插入
     * @param list
     * @return
     */
    boolean batchCreate(List<InventoryMonitorRuleLineBO> list);

    /**
     * 删除
     */
    boolean delete(long id);

    /**
     * 更新
     * @param inventoryMonitorRuleLine
     */
    boolean update(InventoryMonitorRuleLineBO inventoryMonitorRuleLine);

    /**
     * 批量更新规则行
     * @param list
     * @return
     */
    boolean updateBatch(List<InventoryMonitorRuleLineBO> list);

    /**
     * 根据预警规则ID更新规则行
     * @param ruleLineBO
     * @return
     */
    boolean updateByMonitorRuleId(InventoryMonitorRuleLineBO ruleLineBO);

    /**
     * 根据主键 id 查询
     */
    InventoryMonitorRuleLineBO load(long id);

    /**
     * 根据预警规则ID和预警维度查询规则行
     * @param monitorRuleIds
     * @return
     */
    List<InventoryMonitorRuleLineBO> queryByMonitorRuleIds(List<Long> monitorRuleIds);

    /**
     * 分页查询预警规则行信息
     * @param pageQuery
     * @return
     */
    PageResponse<InventoryMonitorRuleLineBO> pageRuleLineList(InventoryMonitorRuleLinePageQuery pageQuery);

    /**
     * 根据ID更新规则行
     * @param ruleLineBO
     * @param ruleIds
     * @return
     */
    boolean updateByIds(InventoryMonitorRuleLineBO ruleLineBO, List<Long> ruleIds);

    /**
     * 根据ids查询规则行
     * @param ruleLineIds
     * @return
     */
    List<InventoryMonitorRuleLineBO> queryByIds(List<Long> ruleLineIds);

    /**
     * 根据条件查询数据
     * @param monitorObjects
     * @param monitorDimension
     * @param tenantId
     * @param enableStatus
     * @return
     */
    List<InventoryMonitorRuleLineBO> queryByMonitorObjects(List<String> monitorObjects, String monitorType, String monitorDimension,
                                                           String tenantId, String enableStatus);

    /**
     * 根据条件查询行
     * @param monitorRuleId
     * @param dimension
     * @param monitorObjects
     * @return
     */
    List<InventoryMonitorRuleLineBO> queryByCondition(Long monitorRuleId, String dimension, List<String> monitorObjects);

}
