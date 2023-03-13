package com.inventory.middle.application.service;


import com.inventory.middle.client.dto.command.InventoryTransitCommand;

import java.util.List;


/**
 * 库存-在途ApplicationService
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
public interface InventoryTransitApplicationService {

    /**
     * 保存
     *
     * @param inventorytransitCommand
     */
    boolean add(InventoryTransitCommand inventorytransitCommand);

    /**
     * 更新
     *
     * @param inventorytransitCommand
     */
    boolean update(InventoryTransitCommand inventorytransitCommand);

    /**
     * 批量删除
     *
     * @param ids
     */
    boolean deleteBatch(List<Long> ids);

}
