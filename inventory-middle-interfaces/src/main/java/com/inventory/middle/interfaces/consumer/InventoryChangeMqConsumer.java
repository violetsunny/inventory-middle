package com.inventory.middle.interfaces.consumer;

import com.alibaba.fastjson.JSON;
import com.inventory.middle.application.service.InventoryMapApplicationService;
import com.inventory.middle.domain.model.bo.mq.sub.InventoryChangeMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import top.kdla.framework.common.aspect.mdc.MdcDot;

import javax.annotation.Resource;

/**
 * 库存变更MQ消费者（计算移动平均价）
 * 迁移自: com.enn.inventory.center.ext.processor.mq.InventoryChangeMqConsumer
 */
@Slf4j
@Component
@RocketMQMessageListener(
    topic = "${rocketmq.consumer.inventory-change.topic:stock-inventory-center-topic}",
    consumerGroup = "${rocketmq.consumer.inventory-change.group:inventory-change-consumer-group}",
    selectorExpression = "${rocketmq.consumer.inventory-change.tag:*}"
)
public class InventoryChangeMqConsumer implements RocketMQListener<String> {

    @Resource
    private InventoryMapApplicationService inventoryMapApplicationService;

    @Override
    @MdcDot(bizCode = "inventoryChangeMq")
    public void onMessage(String message) {
        log.info("InventoryChangeMqConsumer.onMessage message={}", message);
        if (StringUtils.isBlank(message)) {
            log.warn("InventoryChangeMqConsumer.onMessage: empty message, skip");
            return;
        }
        try {
            InventoryChangeMessage changeMessage = JSON.parseObject(message, InventoryChangeMessage.class);
            inventoryMapApplicationService.calFromMessage(changeMessage);
        } catch (Exception e) {
            log.error("InventoryChangeMqConsumer.onMessage failed, message={}", message, e);
            throw new RuntimeException(e);
        }
    }
}
