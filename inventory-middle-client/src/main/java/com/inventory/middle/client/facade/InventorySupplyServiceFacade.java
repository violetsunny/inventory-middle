package com.inventory.middle.client.facade;

import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import com.inventory.middle.client.dto.InventorySupplyDto;
import com.inventory.middle.client.dto.command.InventorySupplyCommand;
import com.inventory.middle.client.dto.query.InventorySupplyPageQuery;

import java.util.List;

/**
 * 库存-供给Facade
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
public interface InventorySupplyServiceFacade {

    /**
     * 分页查询
     *
     * @param inventorysupplyPageQuery
     * @return
     */
    PageResponse<InventorySupplyDto> page(InventorySupplyPageQuery inventorysupplyPageQuery);

    /**
     * 库存-供给list查询
     */
    MultiResponse<InventorySupplyDto> list();

    /**
     * 通过ID获取库存-供给
     *
     * @param id
     * @return
     */
    SingleResponse<InventorySupplyDto> findById(Long id);

    /**
     * 保存
     *
     * @param inventorysupplyCommand
     */
    SingleResponse<Boolean> save(InventorySupplyCommand inventorysupplyCommand);

    /**
     * 更新
     *
     * @param inventorysupplyCommand
     */
    SingleResponse<Boolean> update( InventorySupplyCommand inventorysupplyCommand);

    /**
     * 批量删除
     *
     * @param ids
     */
    SingleResponse<Boolean> delete(List<Long> ids);

}
