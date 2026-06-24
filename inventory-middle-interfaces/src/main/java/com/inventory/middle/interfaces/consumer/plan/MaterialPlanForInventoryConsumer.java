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
import top.kdla.framework.common.aspect.mdc.MdcDot;

import javax.annotation.Resource;
import java.util.Collections;

@Slf4j
@Component
@CatchAndLog
@RocketMQMessageListener(
        topic = "${rocketmq.plan.inventory.topic:scm-plan-inventory-topic}",
        consumerGroup = "${rocketmq.plan.inventory.consumer.group:scm-plan-management-inventory-consumer}",
        selectorExpression = "inboundTag||outboundTag"
)
public class MaterialPlanForInventoryConsumer implements RocketMQListener<String> {

    @Resource
    private PlanMqConsumerService planMqConsumerService;

    @Override
    @MdcDot(bizCode = "materialPlanInventoryMq")
    public void onMessage(String message) {
        log.info("MaterialPlanForInventoryConsumer.onMessage message={}", message);
        if (StringUtils.isBlank(message)) {
            return;
        }
        try {
            MaterialPlanMessage msg = JSON.parseObject(message, MaterialPlanMessage.class);
            planMqConsumerService.handleMaterialPlanGenerate(Collections.singletonList(msg));
        } catch (Exception e) {
            log.error("MaterialPlanForInventoryConsumer error", e);
            throw new RuntimeException(e);
        }
    }
}
