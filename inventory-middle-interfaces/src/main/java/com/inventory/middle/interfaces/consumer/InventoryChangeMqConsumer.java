package com.inventory.middle.interfaces.consumer;

import com.alibaba.fastjson.JSON;
import com.inventory.middle.application.service.InventoryMapApplicationService;
import com.inventory.middle.domain.common.exception.BusinessException;
import com.inventory.middle.domain.model.bo.mq.sub.InventoryChangeMessage;
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
 * 库存变更MQ消费者（计算移动平均价）
 * 迁移自: com.enn.inventory.center.ext.processor.mq.InventoryChangeMqConsumer
 */
@Slf4j
@Component
@RocketMQMessageListener(
    topic = "${rocketmq.consumer.inventory-change.topic:stock-inventory-center-topic}",
    consumerGroup = "${rocketmq.consumer.inventory-change.group:inventory-change-consumer-group}",
    selectorExpression = "${rocketmq.consumer.inventory-change.tag:*}"
)
public class InventoryChangeMqConsumer implements RocketMQListener<String> {

    @Resource
    private InventoryMapApplicationService inventoryMapApplicationService;

    @Resource
    private RedissonClient redissonClient;

    private static final String IDEMPOTENT_PREFIX = "mq:idempotent:inventoryChange:";
    private static final long IDEMPOTENT_TTL_HOURS = 24;

    @Override
    @MdcDot(bizCode = "inventoryChangeMq")
    public void onMessage(String message) {
        log.info("InventoryChangeMqConsumer.onMessage message={}", message);
        if (StringUtils.isBlank(message)) {
            log.warn("InventoryChangeMqConsumer.onMessage: empty message, skip");
            return;
        }
        try {
            String dedupKey = IDEMPOTENT_PREFIX + DigestUtils.md5Hex(message);
            RBucket<String> bucket = redissonClient.getBucket(dedupKey);
            if (!bucket.trySet("1", IDEMPOTENT_TTL_HOURS, TimeUnit.HOURS)) {
                log.info("InventoryChangeMqConsumer.onMessage: duplicate message, skip, dedupKey={}", dedupKey);
                return;
            }
            InventoryChangeMessage changeMessage = JSON.parseObject(message, InventoryChangeMessage.class);
            inventoryMapApplicationService.calFromMessage(changeMessage);
        } catch (Exception e) {
            log.error("InventoryChangeMqConsumer.onMessage failed, message={}", message, e);
            throw new BusinessException("库存变更MQ消费失败", e);
        }
    }
}
