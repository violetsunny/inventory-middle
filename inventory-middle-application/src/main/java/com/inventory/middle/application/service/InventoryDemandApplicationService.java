package com.inventory.middle.application.service;


import com.inventory.middle.client.dto.command.InventoryDemandCommand;

import java.util.List;


/**
 * 库存-需求ApplicationService
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
public interface InventoryDemandApplicationService {

    /**
     * 保存
     *
     * @param inventorydemandCommand
     */
    boolean add(InventoryDemandCommand inventorydemandCommand);

    /**
     * 更新
     *
     * @param inventorydemandCommand
     */
    boolean update(InventoryDemandCommand inventorydemandCommand);

    /**
     * 批量删除
     *
     * @param ids
     */
    boolean deleteBatch(List<Long> ids);

}
