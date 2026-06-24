package com.inventory.middle.interfaces.consumer.plan;

import com.alibaba.fastjson.JSON;
import com.inventory.middle.application.plan.mq.PlanMqConsumerService;
import com.inventory.middle.application.plan.mq.message.ProjectOrderMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import top.kdla.framework.log.catchlog.CatchAndLog;
import top.kdla.framework.common.aspect.mdc.MdcDot;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Component
@CatchAndLog
@RocketMQMessageListener(
        topic = "${rocketmq.plan.demand.topic:scm-plan-demand-topic}",
        consumerGroup = "scm-plan-management-project-order-consumer",
        selectorExpression = "projectOrderTag"
)
public class ProjectOrderMessageConsumer implements RocketMQListener<String> {

    @Resource
    private PlanMqConsumerService planMqConsumerService;

    @Override
    @MdcDot(bizCode = "projectOrderMq")
    public void onMessage(String message) {
        log.info("ProjectOrderMessageConsumer.onMessage message={}", message);
        if (StringUtils.isBlank(message)) {
            return;
        }
        try {
            List<ProjectOrderMessage> messages = JSON.parseArray(message, ProjectOrderMessage.class);
            planMqConsumerService.handleProjectOrder(messages);
        } catch (Exception e) {
            log.error("ProjectOrderMessageConsumer error", e);
            throw new RuntimeException(e);
        }
    }
}
