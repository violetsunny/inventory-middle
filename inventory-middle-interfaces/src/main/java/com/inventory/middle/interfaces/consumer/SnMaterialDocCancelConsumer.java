package com.inventory.middle.interfaces.consumer;

import com.alibaba.fastjson.JSONObject;
import com.inventory.middle.application.service.MaterialDocMainApplicationService;
import com.inventory.middle.domain.model.bo.mq.sub.MaterialDocCancelMessage;
import com.inventory.middle.interfaces.consumer.validator.MaterialDocMqValidator;
import com.inventory.middle.domain.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;
import top.kdla.framework.common.aspect.mdc.MdcDot;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

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

    @Resource
    private RedissonClient redissonClient;

    private static final String IDEMPOTENT_PREFIX = "mq:idempotent:snMaterialDocCancel:";
    private static final long IDEMPOTENT_TTL_HOURS = 24;

    @Override
    @MdcDot(bizCode = "snMaterialDocCancelMq")
    public void onMessage(String message) {
        log.info("SnMaterialDocCancelConsumer.onMessage message={}", message);
        if (StringUtils.isBlank(message)) {
            log.warn("SnMaterialDocCancelConsumer.onMessage: empty message, skip");
            return;
        }
        try {
            String dedupKey = IDEMPOTENT_PREFIX + DigestUtils.md5Hex(message);
            RBucket<String> bucket = redissonClient.getBucket(dedupKey);
            if (!bucket.trySet("1", IDEMPOTENT_TTL_HOURS, TimeUnit.HOURS)) {
                log.info("SnMaterialDocCancelConsumer.onMessage: duplicate message, skip, dedupKey={}", dedupKey);
                return;
            }
            MaterialDocCancelMessage cancelMessage = JSONObject.parseObject(message, MaterialDocCancelMessage.class);
            materialDocMqValidator.validate(cancelMessage);
            materialDocMainApplicationService.reverseMaterialDocFromMessage(cancelMessage);
            log.info("SnMaterialDocCancelConsumer.onMessage processed successfully");
        } catch (Exception e) {
            log.error("SnMaterialDocCancelConsumer.onMessage failed, message={}", message, e);
            throw new BusinessException("SN物料凭证取消MQ消费失败", e);
        }
    }
}
