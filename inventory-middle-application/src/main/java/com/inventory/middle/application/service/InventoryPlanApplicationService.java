package com.inventory.middle.application.service;


import com.inventory.middle.client.dto.command.InventoryPlanCommand;

import java.util.List;


/**
 * 库存-计划ApplicationService
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
public interface InventoryPlanApplicationService {

    /**
     * 保存
     *
     * @param inventoryplanCommand
     */
    boolean add(InventoryPlanCommand inventoryplanCommand);

    /**
     * 更新
     *
     * @param inventoryplanCommand
     */
    boolean update(InventoryPlanCommand inventoryplanCommand);

    /**
     * 批量删除
     *
     * @param ids
     */
    boolean deleteBatch(List<Long> ids);

}
