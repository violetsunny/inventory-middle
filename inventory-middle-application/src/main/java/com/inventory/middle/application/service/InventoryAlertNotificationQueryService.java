package com.inventory.middle.application.service;


import top.kdla.framework.dto.PageResponse;
import com.inventory.middle.client.dto.InventoryAlertNotificationDto;
import com.inventory.middle.client.dto.query.InventoryAlertNotificationPageQuery;

/**
 * 库存报警通知记录QueryService
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
public interface InventoryAlertNotificationQueryService {

    /**
     * 分页查询
     *
     * @param pageQuery
     * @return
     */
    PageResponse<InventoryAlertNotificationDto> queryPage(InventoryAlertNotificationPageQuery pageQuery);


    /**
     * 通过ID获取库存报警通知记录
     *
     * @param id
     * @return
     */
    InventoryAlertNotificationDto findById(Long id);

}
