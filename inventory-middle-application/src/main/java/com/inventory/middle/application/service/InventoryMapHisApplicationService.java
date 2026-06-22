package com.inventory.middle.application.service;


import com.inventory.middle.client.dto.command.InventoryMapHisCommand;

import java.util.List;


/**
 * 移动平均价历史记录ApplicationService
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
public interface InventoryMapHisApplicationService {

    /**
     * 保存
     *
     * @param inventorymaphisCommand
     */
    boolean add(InventoryMapHisCommand inventorymaphisCommand);

    /**
     * 更新
     *
     * @param inventorymaphisCommand
     */
    boolean update(InventoryMapHisCommand inventorymaphisCommand);

    /**
     * 批量删除
     *
     * @param ids
     */
    boolean deleteBatch(List<Long> ids);

}
