package com.inventory.middle.interfaces.consumer;

import com.alibaba.fastjson.JSON;
import com.inventory.middle.application.service.InventoryMonitorRuleApplicationService;
import com.inventory.middle.domain.model.bo.mq.MonitorMessageBO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import com.inventory.middle.domain.common.exception.BusinessException;
import top.kdla.framework.common.aspect.mdc.MdcDot;

import javax.annotation.Resource;

/**
 * 预警规则行 MQ 消费者（规则行新建/更新时触发预警重算）
 * 迁移自: inventory-center MonitorRuleLineConsumer
 * 消费 tag: monitorRuleLineCreateTag || monitorRuleLineUpdateTag
 */
@Slf4j
@Component
@RocketMQMessageListener(
    topic = "${rocketmq.consumer.monitor-rule-line.topic:stock-inventory-center-topic}",
    consumerGroup = "${rocketmq.consumer.monitor-rule-line.group:monitorRuleLine}",
    selectorType = SelectorType.TAG,
    selectorExpression = "monitorRuleLineCreateTag || monitorRuleLineUpdateTag"
)
public class MonitorRuleLineConsumer implements RocketMQListener<String> {

    @Resource
    private InventoryMonitorRuleApplicationService inventoryMonitorRuleApplicationService;

    @Override
    @MdcDot(bizCode = "monitorRuleLineMq")
    public void onMessage(String message) {
        log.info("MonitorRuleLineConsumer.onMessage message={}", message);
        if (StringUtils.isBlank(message)) {
            log.warn("MonitorRuleLineConsumer.onMessage: empty message, skip");
            return;
        }
        try {
            MonitorMessageBO monitorMessageBO = JSON.parseObject(message, MonitorMessageBO.class);
            inventoryMonitorRuleApplicationService.processMonitorMessage(monitorMessageBO);
        } catch (Exception e) {
            log.error("MonitorRuleLineConsumer.onMessage failed, message={}", message, e);
            throw new BusinessException("MQ消费失败", e);
        }
    }
}
