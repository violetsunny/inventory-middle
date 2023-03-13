package com.inventory.middle.domain.repository;

import com.inventory.middle.domain.model.entity.InventoryAlertNotification;
import com.inventory.middle.domain.model.types.InventoryAlertNotificationId;
import top.kdla.framework.dto.PageQuery;
import top.kdla.framework.dto.PageResponse;

import java.util.List;
import java.util.Map;

/**
 * 库存报警通知记录Repository
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
public interface InventoryAlertNotificationRepository {

    /**
    * 分页查询
    *
    * @param pageQuery
    * @param params
    * @return
    */
    PageResponse<InventoryAlertNotification> queryPage(PageQuery pageQuery, Map<String, Object> params);

    /**
     * 通过ID获取库存报警通知记录
     *
     * @param id
     * @return
     */
     InventoryAlertNotification findById(InventoryAlertNotificationId id);

    /**
     * 保存
     *
     * @param inventoryalertnotification
     */
    boolean store(InventoryAlertNotification inventoryalertnotification);

    /**
     * 更新
     *
     * @param inventoryalertnotification
     */
    boolean update(InventoryAlertNotification inventoryalertnotification);

    /**
     * 删除
     *
     * @param ids
     */
    boolean delete(List<InventoryAlertNotificationId> ids);
}
