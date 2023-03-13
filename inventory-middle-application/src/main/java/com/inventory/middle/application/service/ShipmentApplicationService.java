package com.inventory.middle.application.service;


import com.inventory.middle.client.dto.command.ShipmentCommand;

import java.util.List;


/**
 * 交运单ApplicationService
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
public interface ShipmentApplicationService {

    /**
     * 保存
     *
     * @param shipmentCommand
     */
    boolean add(ShipmentCommand shipmentCommand);

    /**
     * 更新
     *
     * @param shipmentCommand
     */
    boolean update(ShipmentCommand shipmentCommand);

    /**
     * 批量删除
     *
     * @param ids
     */
    boolean deleteBatch(List<Long> ids);

}
