package com.inventory.middle.application.service;


import com.inventory.middle.client.dto.command.InventoryMonitorRuleCommand;

import java.util.List;


/**
 * 库存预警规则ApplicationService
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
public interface InventoryMonitorRuleApplicationService {

    /**
     * 保存
     *
     * @param inventorymonitorruleCommand
     */
    boolean add(InventoryMonitorRuleCommand inventorymonitorruleCommand);

    /**
     * 更新
     *
     * @param inventorymonitorruleCommand
     */
    boolean update(InventoryMonitorRuleCommand inventorymonitorruleCommand);

    /**
     * 批量删除
     *
     * @param ids
     */
    boolean deleteBatch(List<Long> ids);

}
