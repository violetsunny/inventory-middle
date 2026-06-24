package com.inventory.middle.interfaces.consumer.plan;

import com.alibaba.fastjson.JSON;
import com.inventory.middle.application.plan.mq.PlanMqConsumerService;
import com.inventory.middle.application.plan.mq.message.SystemPlanOrderCreateMessage;
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
        topic = "plan-scm-plan-management-topic",
        consumerGroup = "SystemPlanOrderCreateConsumer",
        selectorExpression = "systemPlanOrderCreateTag"
)
public class SystemPlanOrderCreateConsumer implements RocketMQListener<String> {

    @Resource
    private PlanMqConsumerService planMqConsumerService;

    @Override
    @MdcDot(bizCode = "systemPlanOrderMq")
    public void onMessage(String message) {
        log.info("SystemPlanOrderCreateConsumer.onMessage message={}", message);
        if (StringUtils.isBlank(message)) {
            return;
        }
        try {
            SystemPlanOrderCreateMessage msg = JSON.parseObject(message, SystemPlanOrderCreateMessage.class);
            planMqConsumerService.handleSystemPlanOrderCreate(msg);
        } catch (Exception e) {
            log.error("SystemPlanOrderCreateConsumer error", e);
            throw new RuntimeException(e);
        }
    }
}
