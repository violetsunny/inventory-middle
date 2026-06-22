package com.inventory.middle.client.facade;

import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import com.inventory.middle.client.dto.InventoryMapHisDto;
import com.inventory.middle.client.dto.command.InventoryMapHisCommand;
import com.inventory.middle.client.dto.query.InventoryMapHisPageQuery;

import java.util.List;

/**
 * 移动平均价历史记录Facade
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
public interface InventoryMapHisServiceFacade {

    /**
     * 分页查询
     *
     * @param inventorymaphisPageQuery
     * @return
     */
    PageResponse<InventoryMapHisDto> page(InventoryMapHisPageQuery inventorymaphisPageQuery);

    /**
     * 移动平均价历史记录list查询
     */
    MultiResponse<InventoryMapHisDto> list();

    /**
     * 通过ID获取移动平均价历史记录
     *
     * @param id
     * @return
     */
    SingleResponse<InventoryMapHisDto> findById(Long id);

    /**
     * 保存
     *
     * @param inventorymaphisCommand
     */
    SingleResponse<Boolean> save(InventoryMapHisCommand inventorymaphisCommand);

    /**
     * 更新
     *
     * @param inventorymaphisCommand
     */
    SingleResponse<Boolean> update( InventoryMapHisCommand inventorymaphisCommand);

    /**
     * 批量删除
     *
     * @param ids
     */
    SingleResponse<Boolean> delete(List<Long> ids);

}
