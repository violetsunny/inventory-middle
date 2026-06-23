package com.inventory.middle.interfaces.consumer;

import com.alibaba.fastjson.JSONObject;
import com.inventory.middle.application.service.MaterialDocMainApplicationService;
import com.inventory.middle.domain.model.bo.mq.sub.MaterialDocInMessage;
import com.inventory.middle.interfaces.consumer.validator.MaterialDocMqValidator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import top.kdla.framework.common.aspect.mdc.MdcDot;

import javax.annotation.Resource;

/**
 * SN物料凭证入库MQ消费者
 * 迁移自: com.enn.inventory.center.processor.mq.SnMaterialDocInConsumer
 */
@Slf4j
@Component
@RocketMQMessageListener(
    topic = "${rocketmq.consumer.sn-material-doc-in.topic:stock-inventory-center-topic}",
    consumerGroup = "${rocketmq.consumer.sn-material-doc-in.group:snMaterialDocIn}",
    selectorExpression = "${rocketmq.consumer.sn-material-doc-in.tag:snMaterialDocInTag}"
)
public class SnMaterialDocInConsumer implements RocketMQListener<String> {

    @Resource
    private MaterialDocMainApplicationService materialDocMainApplicationService;

    @Resource
    private MaterialDocMqValidator materialDocMqValidator;

    @Override
    @MdcDot(bizCode = "snMaterialDocInMq")
    public void onMessage(String message) {
        log.info("SnMaterialDocInConsumer.onMessage message={}", message);
        if (StringUtils.isBlank(message)) {
            log.warn("SnMaterialDocInConsumer.onMessage: empty message, skip");
            return;
        }
        try {
            MaterialDocInMessage inMessage = JSONObject.parseObject(message, MaterialDocInMessage.class);
            materialDocMqValidator.validate(inMessage);
            materialDocMainApplicationService.createMaterialDocFromMessage(inMessage);
            log.info("SnMaterialDocInConsumer.onMessage processed successfully");
        } catch (Exception e) {
            log.error("SnMaterialDocInConsumer.onMessage failed, message={}", message, e);
            throw new RuntimeException(e);
        }
    }
}
