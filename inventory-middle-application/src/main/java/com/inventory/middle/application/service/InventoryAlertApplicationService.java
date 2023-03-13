package com.inventory.middle.application.service;


import com.inventory.middle.client.dto.command.InventoryAlertCommand;

import java.util.List;


/**
 * 库存报警日志ApplicationService
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:29
 */
public interface InventoryAlertApplicationService {

    /**
     * 保存
     *
     * @param inventoryalertCommand
     */
    boolean add(InventoryAlertCommand inventoryalertCommand);

    /**
     * 更新
     *
     * @param inventoryalertCommand
     */
    boolean update(InventoryAlertCommand inventoryalertCommand);

    /**
     * 批量删除
     *
     * @param ids
     */
    boolean deleteBatch(List<Long> ids);

}
