package com.inventory.middle.interfaces.consumer;

import com.alibaba.fastjson.JSON;
import com.inventory.middle.application.service.InventoryAlertNotificationApplicationService;
import com.inventory.middle.domain.model.bo.mq.InventoryAlertMessageBO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import top.kdla.framework.common.aspect.mdc.MdcDot;

import javax.annotation.Resource;

/**
 * 库存预警MQ消费者（监听预警消息，发送邮件通知）
 * 迁移自: com.enn.inventory.center.processor.mq.InventoryAlertConsumer
 */
@Slf4j
@Component
@RocketMQMessageListener(
    topic = "${rocketmq.consumer.inventory-alert.topic:stock-inventory-center-topic}",
    consumerGroup = "${rocketmq.consumer.inventory-alert.group:monitorAlert}",
    selectorExpression = "${rocketmq.consumer.inventory-alert.tag:inventoryAlertCreateTag}"
)
public class InventoryAlertConsumer implements RocketMQListener<String> {

    @Resource
    private InventoryAlertNotificationApplicationService inventoryAlertNotificationApplicationService;

    @Override
    @MdcDot(bizCode = "inventoryAlertMq")
    public void onMessage(String message) {
        log.info("InventoryAlertConsumer.onMessage message={}", message);
        if (StringUtils.isBlank(message)) {
            log.warn("InventoryAlertConsumer.onMessage: empty message, skip");
            return;
        }
        try {
            InventoryAlertMessageBO alertMessageBO = JSON.parseObject(message, InventoryAlertMessageBO.class);
            inventoryAlertNotificationApplicationService.sendAlertNotification(alertMessageBO);
        } catch (Exception e) {
            log.error("InventoryAlertConsumer.onMessage failed, message={}", message, e);
            throw new RuntimeException(e);
        }
    }
}
