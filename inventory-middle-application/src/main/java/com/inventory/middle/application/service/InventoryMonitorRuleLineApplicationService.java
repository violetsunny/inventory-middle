package com.inventory.middle.application.service;


import com.inventory.middle.client.dto.command.InventoryMonitorRuleLineCommand;

import java.util.List;


/**
 * 库存预警规则明细ApplicationService
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
public interface InventoryMonitorRuleLineApplicationService {

    /**
     * 保存
     *
     * @param inventorymonitorrulelineCommand
     */
    boolean add(InventoryMonitorRuleLineCommand inventorymonitorrulelineCommand);

    /**
     * 更新
     *
     * @param inventorymonitorrulelineCommand
     */
    boolean update(InventoryMonitorRuleLineCommand inventorymonitorrulelineCommand);

    /**
     * 批量删除
     *
     * @param ids
     */
    boolean deleteBatch(List<Long> ids);

}
