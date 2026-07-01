package com.inventory.middle.interfaces.consumer;

import com.alibaba.fastjson.JSON;
import com.inventory.middle.application.service.InventoryMonitorRuleApplicationService;
import com.inventory.middle.domain.model.bo.mq.sub.InventoryChangeMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import com.inventory.middle.domain.common.exception.BusinessException;
import top.kdla.framework.common.aspect.mdc.MdcDot;

import javax.annotation.Resource;

/**
 * 出入库库存变化MQ消费者（根据预警规则重新计算预警）
 * 迁移自: com.enn.inventory.center.processor.mq.MonitorRuleInOutBoundConsumer
 */
@Slf4j
@Component
@RocketMQMessageListener(
    topic = "${rocketmq.consumer.monitor-inoutbound.topic:stock-inventory-center-topic}",
    consumerGroup = "${rocketmq.consumer.monitor-inoutbound.group:inOrOutBound}",
    selectorExpression = "${rocketmq.consumer.monitor-inoutbound.tag:*}"
)
public class MonitorRuleInOutBoundConsumer implements RocketMQListener<String> {

    @Resource
    private InventoryMonitorRuleApplicationService inventoryMonitorRuleApplicationService;

    @Override
    @MdcDot(bizCode = "monitorRuleInOutBoundMq")
    public void onMessage(String message) {
        log.info("MonitorRuleInOutBoundConsumer.onMessage message={}", message);
        if (StringUtils.isBlank(message)) {
            log.warn("MonitorRuleInOutBoundConsumer.onMessage: empty message, skip");
            return;
        }
        try {
            InventoryChangeMessage changeMessage = JSON.parseObject(message, InventoryChangeMessage.class);
            inventoryMonitorRuleApplicationService.processInOutBoundMessage(changeMessage);
        } catch (Exception e) {
            log.error("MonitorRuleInOutBoundConsumer.onMessage failed, message={}", message, e);
            throw new BusinessException("MQ消费失败", e);
        }
    }
}
