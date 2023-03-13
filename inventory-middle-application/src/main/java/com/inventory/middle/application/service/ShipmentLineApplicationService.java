package com.inventory.middle.application.service;


import com.inventory.middle.client.dto.command.ShipmentLineCommand;

import java.util.List;


/**
 * 交运单明细ApplicationService
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:33
 */
public interface ShipmentLineApplicationService {

    /**
     * 保存
     *
     * @param shipmentlineCommand
     */
    boolean add(ShipmentLineCommand shipmentlineCommand);

    /**
     * 更新
     *
     * @param shipmentlineCommand
     */
    boolean update(ShipmentLineCommand shipmentlineCommand);

    /**
     * 批量删除
     *
     * @param ids
     */
    boolean deleteBatch(List<Long> ids);

}
