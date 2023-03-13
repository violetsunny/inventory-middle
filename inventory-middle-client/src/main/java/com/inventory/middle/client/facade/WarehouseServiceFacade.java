package com.inventory.middle.client.facade;

import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import com.inventory.middle.client.dto.WarehouseDto;
import com.inventory.middle.client.dto.command.WarehouseCommand;
import com.inventory.middle.client.dto.query.WarehousePageQuery;

import java.util.List;

/**
 * 物理仓库表Facade
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
public interface WarehouseServiceFacade {

    /**
     * 分页查询
     *
     * @param warehousePageQuery
     * @return
     */
    PageResponse<WarehouseDto> page(WarehousePageQuery warehousePageQuery);

    /**
     * 物理仓库表list查询
     */
    MultiResponse<WarehouseDto> list();

    /**
     * 通过ID获取物理仓库表
     *
     * @param id
     * @return
     */
    SingleResponse<WarehouseDto> findById(Long id);

    /**
     * 保存
     *
     * @param warehouseCommand
     */
    SingleResponse<Boolean> save(WarehouseCommand warehouseCommand);

    /**
     * 更新
     *
     * @param warehouseCommand
     */
    SingleResponse<Boolean> update( WarehouseCommand warehouseCommand);

    /**
     * 批量删除
     *
     * @param ids
     */
    SingleResponse<Boolean> delete(List<Long> ids);

}
