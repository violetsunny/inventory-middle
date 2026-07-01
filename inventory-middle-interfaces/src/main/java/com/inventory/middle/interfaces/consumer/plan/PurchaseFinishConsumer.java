package com.inventory.middle.interfaces.consumer.plan;

import com.alibaba.fastjson.JSON;
import com.inventory.middle.application.plan.mq.PlanMqConsumerService;
import com.inventory.middle.application.plan.mq.message.PurchaseFinishMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import top.kdla.framework.log.catchlog.CatchAndLog;
import com.inventory.middle.domain.common.exception.BusinessException;
import top.kdla.framework.common.aspect.mdc.MdcDot;

import javax.annotation.Resource;

@Slf4j
@Component
@CatchAndLog
@RocketMQMessageListener(
        topic = "plan-scm-plan-management-topic",
        consumerGroup = "PurchaseFinishConsumer",
        selectorExpression = "purchaseFinishTag"
)
public class PurchaseFinishConsumer implements RocketMQListener<String> {

    @Resource
    private PlanMqConsumerService planMqConsumerService;

    @Override
    @MdcDot(bizCode = "purchaseFinishMq")
    public void onMessage(String message) {
        log.info("PurchaseFinishConsumer.onMessage message={}", message);
        if (StringUtils.isBlank(message)) {
            return;
        }
        try {
            PurchaseFinishMessage msg = JSON.parseObject(message, PurchaseFinishMessage.class);
            planMqConsumerService.handlePurchaseFinish(msg);
        } catch (Exception e) {
            log.error("PurchaseFinishConsumer error", e);
            throw new BusinessException("MQ消费失败", e);
        }
    }
}
