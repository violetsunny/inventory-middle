package com.inventory.middle.interfaces.consumer;

import com.alibaba.fastjson.JSONObject;
import com.inventory.middle.application.service.MaterialDocMainApplicationService;
import com.inventory.middle.domain.model.bo.mq.sub.MaterialDocCancelMessage;
import com.inventory.middle.interfaces.consumer.validator.MaterialDocMqValidator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import top.kdla.framework.common.aspect.mdc.MdcDot;

import javax.annotation.Resource;

/**
 * SN物料凭证取消MQ消费者
 * 迁移自: com.enn.inventory.center.processor.mq.SnMaterialDocCancelConsumer
 */
@Slf4j
@Component
@RocketMQMessageListener(
    topic = "${rocketmq.consumer.sn-material-doc-cancel.topic:stock-inventory-center-topic}",
    consumerGroup = "${rocketmq.consumer.sn-material-doc-cancel.group:snMaterialDocCancel}",
    selectorExpression = "${rocketmq.consumer.sn-material-doc-cancel.tag:snMaterialDocCancelTag}"
)
public class SnMaterialDocCancelConsumer implements RocketMQListener<String> {

    @Resource
    private MaterialDocMainApplicationService materialDocMainApplicationService;

    @Resource
    private MaterialDocMqValidator materialDocMqValidator;

    @Override
    @MdcDot(bizCode = "snMaterialDocCancelMq")
    public void onMessage(String message) {
        log.info("SnMaterialDocCancelConsumer.onMessage message={}", message);
        if (StringUtils.isBlank(message)) {
            log.warn("SnMaterialDocCancelConsumer.onMessage: empty message, skip");
            return;
        }
        try {
            MaterialDocCancelMessage cancelMessage = JSONObject.parseObject(message, MaterialDocCancelMessage.class);
            materialDocMqValidator.validate(cancelMessage);
            materialDocMainApplicationService.reverseMaterialDocFromMessage(cancelMessage);
            log.info("SnMaterialDocCancelConsumer.onMessage processed successfully");
        } catch (Exception e) {
            log.error("SnMaterialDocCancelConsumer.onMessage failed, message={}", message, e);
            throw new RuntimeException(e);
        }
    }
}
