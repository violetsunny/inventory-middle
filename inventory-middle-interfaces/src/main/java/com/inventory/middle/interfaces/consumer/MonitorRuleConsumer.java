package com.inventory.middle.interfaces.consumer;

import com.alibaba.fastjson.JSON;
import com.inventory.middle.application.service.InventoryMonitorRuleApplicationService;
import com.inventory.middle.domain.model.bo.mq.MonitorMessageBO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import top.kdla.framework.common.aspect.mdc.MdcDot;

import javax.annotation.Resource;

/**
 * 预警规则MQ消费者（预警规则变更时重新计算预警）
 * 迁移自: com.enn.inventory.center.processor.mq.MonitorRuleConsumer
 */
@Slf4j
@Component
@RocketMQMessageListener(
    topic = "${rocketmq.consumer.monitor-rule.topic:stock-inventory-center-topic}",
    consumerGroup = "${rocketmq.consumer.monitor-rule.group:monitorRule}",
    selectorExpression = "${rocketmq.consumer.monitor-rule.tag:*}"
)
public class MonitorRuleConsumer implements RocketMQListener<String> {

    @Resource
    private InventoryMonitorRuleApplicationService inventoryMonitorRuleApplicationService;

    @Override
    @MdcDot(bizCode = "monitorRuleMq")
    public void onMessage(String message) {
        log.info("MonitorRuleConsumer.onMessage message={}", message);
        if (StringUtils.isBlank(message)) {
            log.warn("MonitorRuleConsumer.onMessage: empty message, skip");
            return;
        }
        try {
            MonitorMessageBO monitorMessageBO = JSON.parseObject(message, MonitorMessageBO.class);
            inventoryMonitorRuleApplicationService.processMonitorMessage(monitorMessageBO);
        } catch (Exception e) {
            log.error("MonitorRuleConsumer.onMessage failed, message={}", message, e);
            throw new RuntimeException(e);
        }
    }
}
