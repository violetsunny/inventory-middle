package com.inventory.middle.client.facade;

import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import com.inventory.middle.client.dto.InventorySnapshotDto;
import com.inventory.middle.client.dto.command.InventorySnapshotCommand;
import com.inventory.middle.client.dto.query.InventorySnapshotPageQuery;

import java.util.List;

/**
 * 库存快照-实时Facade
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
public interface InventorySnapshotServiceFacade {

    /**
     * 分页查询
     *
     * @param inventorysnapshotPageQuery
     * @return
     */
    PageResponse<InventorySnapshotDto> page(InventorySnapshotPageQuery inventorysnapshotPageQuery);

    /**
     * 库存快照-实时list查询
     */
    MultiResponse<InventorySnapshotDto> list();

    /**
     * 通过ID获取库存快照-实时
     *
     * @param id
     * @return
     */
    SingleResponse<InventorySnapshotDto> findById(Long id);

    /**
     * 保存
     *
     * @param inventorysnapshotCommand
     */
    SingleResponse<Boolean> save(InventorySnapshotCommand inventorysnapshotCommand);

    /**
     * 更新
     *
     * @param inventorysnapshotCommand
     */
    SingleResponse<Boolean> update( InventorySnapshotCommand inventorysnapshotCommand);

    /**
     * 批量删除
     *
     * @param ids
     */
    SingleResponse<Boolean> delete(List<Long> ids);

}
