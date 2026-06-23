package com.inventory.middle.infra.mq;

import com.alibaba.fastjson.JSON;
import com.inventory.middle.domain.model.enums.InventoryTagEnum;
import com.inventory.middle.domain.service.mq.InventoryMqProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 库存 MQ 生产者实现（平替 RdfaMqClient）
 */
@Component
@Slf4j
public class InventoryMqProducerImpl implements InventoryMqProducer {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Value("${rocketmq.topic:inventory-topic}")
    private String topic;

    @Override
    public void sendInventoryChange(Object message, InventoryTagEnum tagEnum) {
        doSend(message, tagEnum);
    }

    @Override
    public void sendMonitor(Object message, InventoryTagEnum tagEnum) {
        doSend(message, tagEnum);
    }

    @Override
    public void sendAnnualInspection(Object message, InventoryTagEnum tagEnum) {
        doSend(message, tagEnum);
    }

    @Override
    public void sendQuantityMonitor(Object message, InventoryTagEnum tagEnum) {
        doSend(message, tagEnum);
    }

    private void doSend(Object message, InventoryTagEnum tagEnum) {
        try {
            String destination = topic + ":" + tagEnum.getCode();
            rocketMQTemplate.syncSend(destination, JSON.toJSONString(message));
            log.info("InventoryMqProducer.send tag={} msg={}", tagEnum.getCode(), JSON.toJSONString(message));
        } catch (Exception e) {
            log.error("InventoryMqProducer.send error tag={}", tagEnum.getCode(), e);
        }
    }
}

