package com.inventory.middle.application.service;


import com.inventory.middle.client.dto.command.InventorySnapshotCommand;

import java.util.List;


/**
 * 库存快照-实时ApplicationService
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
public interface InventorySnapshotApplicationService {

    /**
     * 保存
     *
     * @param inventorysnapshotCommand
     */
    boolean add(InventorySnapshotCommand inventorysnapshotCommand);

    /**
     * 更新
     *
     * @param inventorysnapshotCommand
     */
    boolean update(InventorySnapshotCommand inventorysnapshotCommand);

    /**
     * 批量删除
     *
     * @param ids
     */
    boolean deleteBatch(List<Long> ids);

}
