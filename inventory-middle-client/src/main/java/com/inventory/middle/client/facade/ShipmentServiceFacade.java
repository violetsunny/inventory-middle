package com.inventory.middle.client.facade;

import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import com.inventory.middle.client.dto.ShipmentDto;
import com.inventory.middle.client.dto.command.ShipmentCommand;
import com.inventory.middle.client.dto.query.ShipmentPageQuery;

import java.util.List;

/**
 * 交运单Facade
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
public interface ShipmentServiceFacade {

    /**
     * 分页查询
     *
     * @param shipmentPageQuery
     * @return
     */
    PageResponse<ShipmentDto> page(ShipmentPageQuery shipmentPageQuery);

    /**
     * 交运单list查询
     */
    MultiResponse<ShipmentDto> list();

    /**
     * 通过ID获取交运单
     *
     * @param id
     * @return
     */
    SingleResponse<ShipmentDto> findById(Long id);

    /**
     * 保存
     *
     * @param shipmentCommand
     */
    SingleResponse<Boolean> save(ShipmentCommand shipmentCommand);

    /**
     * 更新
     *
     * @param shipmentCommand
     */
    SingleResponse<Boolean> update( ShipmentCommand shipmentCommand);

    /**
     * 批量删除
     *
     * @param ids
     */
    SingleResponse<Boolean> delete(List<Long> ids);

}
