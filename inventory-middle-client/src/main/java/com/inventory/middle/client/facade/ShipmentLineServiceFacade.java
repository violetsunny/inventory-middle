package com.inventory.middle.client.facade;

import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import com.inventory.middle.client.dto.ShipmentLineDto;
import com.inventory.middle.client.dto.command.ShipmentLineCommand;
import com.inventory.middle.client.dto.query.ShipmentLinePageQuery;

import java.util.List;

/**
 * 交运单明细Facade
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:33
 */
public interface ShipmentLineServiceFacade {

    /**
     * 分页查询
     *
     * @param shipmentlinePageQuery
     * @return
     */
    PageResponse<ShipmentLineDto> page(ShipmentLinePageQuery shipmentlinePageQuery);

    /**
     * 交运单明细list查询
     */
    MultiResponse<ShipmentLineDto> list();

    /**
     * 通过ID获取交运单明细
     *
     * @param id
     * @return
     */
    SingleResponse<ShipmentLineDto> findById(Long id);

    /**
     * 保存
     *
     * @param shipmentlineCommand
     */
    SingleResponse<Boolean> save(ShipmentLineCommand shipmentlineCommand);

    /**
     * 更新
     *
     * @param shipmentlineCommand
     */
    SingleResponse<Boolean> update( ShipmentLineCommand shipmentlineCommand);

    /**
     * 批量删除
     *
     * @param ids
     */
    SingleResponse<Boolean> delete(List<Long> ids);

}
