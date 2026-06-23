package com.inventory.middle.domain.service;

import com.inventory.middle.domain.model.bo.monitor.InventoryAlertNotificationBO;
import top.kdla.framework.dto.SingleResponse;

import java.util.List;

/**
 * @description 库存报警通知记录
 * @author dongguo.tao
 * @date 2021-06-16
 */
public interface InventoryAlertNotificationCoreService {

    /**
     * 新增
     */
    SingleResponse<InventoryAlertNotificationBO> insert(InventoryAlertNotificationBO alertNotificationBO);

    /**
     * 批量插入
     * @param boList
     * @return
     */
    boolean insertBatch(List<InventoryAlertNotificationBO> boList);

    /**
     * 根据alertIds批量删除
     * @param alertNotificationBO
     * @param alertIds
     * @return
     */
    boolean deleteByAlertIds(InventoryAlertNotificationBO alertNotificationBO, List<Long> alertIds);

    /**
     * 更新
     */
    boolean update(InventoryAlertNotificationBO alertNotificationBO);

    /**
     * 根据ID集合批量更新消息发送状态
     * @param ids
     * @return
     */
    boolean updateStatus(List<Long> ids, Integer status);

    /**
     * 根据主键 id 查询
     */
    InventoryAlertNotificationBO load(long id);

    /**
     * 根据alertId集合查询
     * @param alertIds
     * @param status
     * @return
     */
    List<InventoryAlertNotificationBO> queryByStatusAndAlertIds(List<Long> alertIds, Integer status);


}
