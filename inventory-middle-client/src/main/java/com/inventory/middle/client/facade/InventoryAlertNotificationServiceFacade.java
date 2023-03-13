package com.inventory.middle.client.facade;

import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import com.inventory.middle.client.dto.InventoryAlertNotificationDto;
import com.inventory.middle.client.dto.command.InventoryAlertNotificationCommand;
import com.inventory.middle.client.dto.query.InventoryAlertNotificationPageQuery;

import java.util.List;

/**
 * 库存报警通知记录Facade
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
public interface InventoryAlertNotificationServiceFacade {

    /**
     * 分页查询
     *
     * @param inventoryalertnotificationPageQuery
     * @return
     */
    PageResponse<InventoryAlertNotificationDto> page(InventoryAlertNotificationPageQuery inventoryalertnotificationPageQuery);

    /**
     * 库存报警通知记录list查询
     */
    MultiResponse<InventoryAlertNotificationDto> list();

    /**
     * 通过ID获取库存报警通知记录
     *
     * @param id
     * @return
     */
    SingleResponse<InventoryAlertNotificationDto> findById(Long id);

    /**
     * 保存
     *
     * @param inventoryalertnotificationCommand
     */
    SingleResponse<Boolean> save(InventoryAlertNotificationCommand inventoryalertnotificationCommand);

    /**
     * 更新
     *
     * @param inventoryalertnotificationCommand
     */
    SingleResponse<Boolean> update( InventoryAlertNotificationCommand inventoryalertnotificationCommand);

    /**
     * 批量删除
     *
     * @param ids
     */
    SingleResponse<Boolean> delete(List<Long> ids);

}
