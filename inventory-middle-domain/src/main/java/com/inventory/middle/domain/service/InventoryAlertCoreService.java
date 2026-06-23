package com.inventory.middle.domain.service;

import com.inventory.middle.domain.model.bo.monitor.InventoryAlertBO;

import java.util.List;

/**
 * @description 库存报警日志
 * @author dongguo.tao
 * @date 2021-06-16
 */
public interface InventoryAlertCoreService {

    /**
     * 批量插入
     * @param alertBOs
     * @return
     */
    List<InventoryAlertBO> insertBatch(List<InventoryAlertBO> alertBOs);

    /**
     * 根据预警规则ID更新
     * @param alertBO
     * @return
     */
    boolean deleteByMonitorRuleId(InventoryAlertBO alertBO);

    /**
     * 根据ids批量删除预警信息
     * @param alertBO
     * @param alertIds
     * @return
     */
    boolean deleteByIds(InventoryAlertBO alertBO, List<Long> alertIds);

    /**
     * 根据主键 id 查询
     */
    InventoryAlertBO load(long id);

    /**
     * 根据alertIds查询
     * @param monitorRuleIds
     * @return
     */
    List<InventoryAlertBO> queryByMonitorRuleIds(List<Long> monitorRuleIds);

    /**
     * 根据物料编码查询
     * @param queryByMaterialCodes
     * @param alertBO
     * @return
     */
    List<InventoryAlertBO> queryByMaterialCodes(List<String> queryByMaterialCodes, InventoryAlertBO alertBO);

    /**
     * 把相关报警修改为删除状态
     * @param alertList
     */
    void deleteByMaterialAndPlant(List<InventoryAlertBO> alertList);
}
