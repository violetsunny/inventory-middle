package com.inventory.middle.interfaces.consumer;

import com.alibaba.fastjson.JSON;
import com.inventory.middle.client.code.dto.request.ManufacturerInStockRequest;
import com.inventory.middle.application.service.AccessoriesFlowCodeApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import com.inventory.middle.domain.common.exception.BusinessException;
import top.kdla.framework.common.aspect.mdc.MdcDot;

import javax.annotation.Resource;

/**
 * BFF端库存MQ消费者（处理厂商入库消息）
 * 迁移自: com.enn.inventory.center.ext.processor.mq.InventoryBffMqConsumer
 */
@Slf4j
@Component
@RocketMQMessageListener(
    topic = "${rocketmq.consumer.inventory-bff.topic:inventory-bff-topic}",
    consumerGroup = "${rocketmq.consumer.inventory-bff.group:inv_ext_for_bff}",
    selectorExpression = "${rocketmq.consumer.inventory-bff.tag:*}"
)
public class InventoryBffMqConsumer implements RocketMQListener<String> {

    @Resource
    private AccessoriesFlowCodeApplicationService accessoriesFlowCodeApplicationService;

    @Override
    @MdcDot(bizCode = "inventoryBffMq")
    public void onMessage(String message) {
        log.info("InventoryBffMqConsumer.onMessage message={}", JSON.toJSONString(message));
        try {
            ManufacturerInStockRequest request = JSON.parseObject(message, ManufacturerInStockRequest.class);
            accessoriesFlowCodeApplicationService.manufacturerInStock(request);
        } catch (Exception e) {
            log.error("InventoryBffMqConsumer.onMessage failed, message={}", JSON.toJSONString(message), e);
            throw new BusinessException("MQ消费失败", e);
        }
    }
}
