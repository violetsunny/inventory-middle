package com.inventory.middle.client.facade;

import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import com.inventory.middle.client.dto.InventoryDemandDto;
import com.inventory.middle.client.dto.command.InventoryDemandCommand;
import com.inventory.middle.client.dto.query.InventoryDemandPageQuery;

import java.util.List;

/**
 * 库存-需求Facade
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
public interface InventoryDemandServiceFacade {

    /**
     * 分页查询
     *
     * @param inventorydemandPageQuery
     * @return
     */
    PageResponse<InventoryDemandDto> page(InventoryDemandPageQuery inventorydemandPageQuery);

    /**
     * 库存-需求list查询
     */
    MultiResponse<InventoryDemandDto> list();

    /**
     * 通过ID获取库存-需求
     *
     * @param id
     * @return
     */
    SingleResponse<InventoryDemandDto> findById(Long id);

    /**
     * 保存
     *
     * @param inventorydemandCommand
     */
    SingleResponse<Boolean> save(InventoryDemandCommand inventorydemandCommand);

    /**
     * 更新
     *
     * @param inventorydemandCommand
     */
    SingleResponse<Boolean> update( InventoryDemandCommand inventorydemandCommand);

    /**
     * 批量删除
     *
     * @param ids
     */
    SingleResponse<Boolean> delete(List<Long> ids);

}
