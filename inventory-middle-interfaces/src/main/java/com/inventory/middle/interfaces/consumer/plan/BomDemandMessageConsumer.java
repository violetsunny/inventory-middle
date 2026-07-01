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
import com.inventory.middle.domain.common.exception.BusinessException;
import top.kdla.framework.common.aspect.mdc.MdcDot;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Component
@CatchAndLog
@RocketMQMessageListener(
        topic = "${rocketmq.plan.demand.topic:scm-plan-demand-topic}",
        consumerGroup = "scm-plan-management-bom-demand-consumer",
        selectorExpression = "bomDemandTag"
)
public class BomDemandMessageConsumer implements RocketMQListener<String> {

    @Resource
    private PlanMqConsumerService planMqConsumerService;

    @Override
    @MdcDot(bizCode = "bomDemandMq")
    public void onMessage(String message) {
        log.info("BomDemandMessageConsumer.onMessage message={}", message);
        if (StringUtils.isBlank(message)) {
            return;
        }
        try {
            List<DemandSupplyMessage> messages = JSON.parseArray(message, DemandSupplyMessage.class);
            planMqConsumerService.handleDemandSupplySourceBatch(messages);
        } catch (Exception e) {
            log.error("BomDemandMessageConsumer error", e);
            throw new BusinessException("MQ消费失败", e);
        }
    }
}
