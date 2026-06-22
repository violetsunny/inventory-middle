package com.inventory.middle.application.service;


import com.inventory.middle.client.dto.command.InventoryAlertNotificationCommand;
import com.inventory.middle.domain.model.bo.mq.InventoryAlertMessageBO;

import java.util.List;


/**
 * 库存报警通知记录ApplicationService
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
public interface InventoryAlertNotificationApplicationService {

    /**
     * 保存
     *
     * @param inventoryalertnotificationCommand
     */
    boolean add(InventoryAlertNotificationCommand inventoryalertnotificationCommand);

    /**
     * 更新
     *
     * @param inventoryalertnotificationCommand
     */
    boolean update(InventoryAlertNotificationCommand inventoryalertnotificationCommand);

    /**
     * 批量删除
     *
     * @param ids
     */
    boolean deleteBatch(List<Long> ids);

    /**
     * 处理预警通知消息（MQ触发，发送邮件/钉钉通知）
     *
     * @param alertMessageBO 预警消息体
     */
    void sendAlertNotification(InventoryAlertMessageBO alertMessageBO);

}
