package com.inventory.middle.application.service;


import com.inventory.middle.client.dto.command.WarehouseCommand;

import java.util.List;


/**
 * 物理仓库表ApplicationService
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
public interface WarehouseApplicationService {

    /**
     * 保存
     *
     * @param warehouseCommand
     */
    boolean add(WarehouseCommand warehouseCommand);

    /**
     * 更新
     *
     * @param warehouseCommand
     */
    boolean update(WarehouseCommand warehouseCommand);

    /**
     * 批量删除
     *
     * @param ids
     */
    boolean deleteBatch(List<Long> ids);

}
