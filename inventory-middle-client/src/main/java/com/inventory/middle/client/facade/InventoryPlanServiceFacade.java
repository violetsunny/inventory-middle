package com.inventory.middle.client.facade;

import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import com.inventory.middle.client.dto.InventoryPlanDto;
import com.inventory.middle.client.dto.command.InventoryPlanCommand;
import com.inventory.middle.client.dto.query.InventoryPlanPageQuery;

import java.util.List;

/**
 * 库存-计划Facade
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
public interface InventoryPlanServiceFacade {

    /**
     * 分页查询
     *
     * @param inventoryplanPageQuery
     * @return
     */
    PageResponse<InventoryPlanDto> page(InventoryPlanPageQuery inventoryplanPageQuery);

    /**
     * 库存-计划list查询
     */
    MultiResponse<InventoryPlanDto> list();

    /**
     * 通过ID获取库存-计划
     *
     * @param id
     * @return
     */
    SingleResponse<InventoryPlanDto> findById(Long id);

    /**
     * 保存
     *
     * @param inventoryplanCommand
     */
    SingleResponse<Boolean> save(InventoryPlanCommand inventoryplanCommand);

    /**
     * 更新
     *
     * @param inventoryplanCommand
     */
    SingleResponse<Boolean> update( InventoryPlanCommand inventoryplanCommand);

    /**
     * 批量删除
     *
     * @param ids
     */
    SingleResponse<Boolean> delete(List<Long> ids);

}
