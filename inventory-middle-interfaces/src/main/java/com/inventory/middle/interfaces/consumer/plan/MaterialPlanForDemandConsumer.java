package com.inventory.middle.interfaces.consumer.plan;

import com.alibaba.fastjson.JSON;
import com.inventory.middle.application.plan.mq.PlanMqConsumerService;
import com.inventory.middle.application.plan.mq.message.MaterialPlanMessage;
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
        consumerGroup = "${rocketmq.plan.demand.consumer.group:scm-plan-management-demand-consumer}",
        selectorExpression = "planDemandTag"
)
public class MaterialPlanForDemandConsumer implements RocketMQListener<String> {

    @Resource
    private PlanMqConsumerService planMqConsumerService;

    @Override
    @MdcDot(bizCode = "materialPlanDemandMq")
    public void onMessage(String message) {
        log.info("MaterialPlanForDemandConsumer.onMessage message={}", message);
        if (StringUtils.isBlank(message)) {
            return;
        }
        try {
            List<MaterialPlanMessage> messages = JSON.parseArray(message, MaterialPlanMessage.class);
            planMqConsumerService.handleMaterialPlanGenerate(messages);
        } catch (Exception e) {
            log.error("MaterialPlanForDemandConsumer error", e);
            throw new BusinessException("MQ消费失败", e);
        }
    }
}
