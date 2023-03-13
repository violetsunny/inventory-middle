package com.inventory.middle.client.facade;

import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import com.inventory.middle.client.dto.InventoryAlertDto;
import com.inventory.middle.client.dto.command.InventoryAlertCommand;
import com.inventory.middle.client.dto.query.InventoryAlertPageQuery;

import java.util.List;

/**
 * 库存报警日志Facade
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:29
 */
public interface InventoryAlertServiceFacade {

    /**
     * 分页查询
     *
     * @param inventoryalertPageQuery
     * @return
     */
    PageResponse<InventoryAlertDto> page(InventoryAlertPageQuery inventoryalertPageQuery);

    /**
     * 库存报警日志list查询
     */
    MultiResponse<InventoryAlertDto> list();

    /**
     * 通过ID获取库存报警日志
     *
     * @param id
     * @return
     */
    SingleResponse<InventoryAlertDto> findById(Long id);

    /**
     * 保存
     *
     * @param inventoryalertCommand
     */
    SingleResponse<Boolean> save(InventoryAlertCommand inventoryalertCommand);

    /**
     * 更新
     *
     * @param inventoryalertCommand
     */
    SingleResponse<Boolean> update( InventoryAlertCommand inventoryalertCommand);

    /**
     * 批量删除
     *
     * @param ids
     */
    SingleResponse<Boolean> delete(List<Long> ids);

}
