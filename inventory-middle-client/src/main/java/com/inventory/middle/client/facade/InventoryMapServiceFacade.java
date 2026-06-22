package com.inventory.middle.client.facade;

import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import com.inventory.middle.client.dto.InventoryMapDto;
import com.inventory.middle.client.dto.command.InventoryMapCommand;
import com.inventory.middle.client.dto.query.InventoryMapPageQuery;

import java.util.List;

/**
 * 移动平均价Facade
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
public interface InventoryMapServiceFacade {

    /**
     * 分页查询
     *
     * @param inventorymapPageQuery
     * @return
     */
    PageResponse<InventoryMapDto> page(InventoryMapPageQuery inventorymapPageQuery);

    /**
     * 移动平均价list查询
     */
    MultiResponse<InventoryMapDto> list();

    /**
     * 通过ID获取移动平均价
     *
     * @param id
     * @return
     */
    SingleResponse<InventoryMapDto> findById(Long id);

    /**
     * 保存
     *
     * @param inventorymapCommand
     */
    SingleResponse<Boolean> save(InventoryMapCommand inventorymapCommand);

    /**
     * 更新
     *
     * @param inventorymapCommand
     */
    SingleResponse<Boolean> update( InventoryMapCommand inventorymapCommand);

    /**
     * 批量删除
     *
     * @param ids
     */
    SingleResponse<Boolean> delete(List<Long> ids);

}
