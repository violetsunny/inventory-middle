package com.inventory.middle.application.service;


import com.inventory.middle.client.dto.command.InventoryMapCommand;

import java.util.List;


/**
 * 移动平均价ApplicationService
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
public interface InventoryMapApplicationService {

    /**
     * 保存
     *
     * @param inventorymapCommand
     */
    boolean add(InventoryMapCommand inventorymapCommand);

    /**
     * 更新
     *
     * @param inventorymapCommand
     */
    boolean update(InventoryMapCommand inventorymapCommand);

    /**
     * 批量删除
     *
     * @param ids
     */
    boolean deleteBatch(List<Long> ids);

}
