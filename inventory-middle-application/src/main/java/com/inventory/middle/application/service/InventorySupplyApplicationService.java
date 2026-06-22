package com.inventory.middle.application.service;


import com.inventory.middle.client.dto.command.InventorySupplyCommand;

import java.util.List;


/**
 * 库存-供给ApplicationService
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
public interface InventorySupplyApplicationService {

    /**
     * 保存
     *
     * @param inventorysupplyCommand
     */
    boolean add(InventorySupplyCommand inventorysupplyCommand);

    /**
     * 更新
     *
     * @param inventorysupplyCommand
     */
    boolean update(InventorySupplyCommand inventorysupplyCommand);

    /**
     * 批量删除
     *
     * @param ids
     */
    boolean deleteBatch(List<Long> ids);

}
