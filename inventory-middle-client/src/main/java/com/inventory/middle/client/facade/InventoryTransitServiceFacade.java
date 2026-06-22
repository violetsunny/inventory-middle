package com.inventory.middle.client.facade;

import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import com.inventory.middle.client.dto.InventoryTransitDto;
import com.inventory.middle.client.dto.command.InventoryTransitCommand;
import com.inventory.middle.client.dto.query.InventoryTransitPageQuery;

import java.util.List;

/**
 * 库存-在途Facade
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
public interface InventoryTransitServiceFacade {

    /**
     * 分页查询
     *
     * @param inventorytransitPageQuery
     * @return
     */
    PageResponse<InventoryTransitDto> page(InventoryTransitPageQuery inventorytransitPageQuery);

    /**
     * 库存-在途list查询
     */
    MultiResponse<InventoryTransitDto> list();

    /**
     * 通过ID获取库存-在途
     *
     * @param id
     * @return
     */
    SingleResponse<InventoryTransitDto> findById(Long id);

    /**
     * 保存
     *
     * @param inventorytransitCommand
     */
    SingleResponse<Boolean> save(InventoryTransitCommand inventorytransitCommand);

    /**
     * 更新
     *
     * @param inventorytransitCommand
     */
    SingleResponse<Boolean> update( InventoryTransitCommand inventorytransitCommand);

    /**
     * 批量删除
     *
     * @param ids
     */
    SingleResponse<Boolean> delete(List<Long> ids);

}
