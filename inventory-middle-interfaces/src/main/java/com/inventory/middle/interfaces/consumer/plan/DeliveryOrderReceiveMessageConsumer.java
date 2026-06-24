package com.inventory.middle.interfaces.consumer.plan;

import com.alibaba.fastjson.JSON;
import com.inventory.middle.application.plan.mq.PlanMqConsumerService;
import com.inventory.middle.application.plan.mq.message.DemandSupplyMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import top.kdla.framework.log.catchlog.CatchAndLog;
import top.kdla.framework.common.aspect.mdc.MdcDot;

import javax.annotation.Resource;

@Slf4j
@Component
@CatchAndLog
@RocketMQMessageListener(
        topic = "${rocketmq.plan.demand.topic:scm-plan-demand-topic}",
        consumerGroup = "scm-plan-management-delivery-order-receive-consumer",
        selectorExpression = "deliveryOrderReceiveTag"
)
public class DeliveryOrderReceiveMessageConsumer implements RocketMQListener<String> {

    @Resource
    private PlanMqConsumerService planMqConsumerService;

    @Override
    @MdcDot(bizCode = "deliveryOrderReceiveMq")
    public void onMessage(String message) {
        log.info("DeliveryOrderReceiveMessageConsumer.onMessage message={}", message);
        if (StringUtils.isBlank(message)) {
            return;
        }
        try {
            DemandSupplyMessage msg = JSON.parseObject(message, DemandSupplyMessage.class);
            planMqConsumerService.handleDemandSupplySource(msg);
        } catch (Exception e) {
            log.error("DeliveryOrderReceiveMessageConsumer error", e);
            throw new RuntimeException(e);
        }
    }
}
