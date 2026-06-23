package com.inventory.middle.application.plan.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Plan 模块默认 MQ 消息生产者
 * <p>
 * 迁移自 com.enn.plan.management.core.mq.DefaultMqProducer（原 rdfa 框架）
 * 改为基于 RocketMQ Spring Boot Starter 发送消息
 * </p>
 *
 * @author migrated from scm-plan-management
 */
@Component
@Slf4j
public class DefaultMqProducer {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Value("${rocketmq.plan.demand.topic:scm-plan-demand-topic}")
    private String topic;

    /**
     * 发送 MQ 消息
     *
     * @param message 消息内容（JSON 字符串）
     * @param tag     消息 tag
     */
    public void doSend(String message, String tag) {
        log.info("DefaultMqProducer send MQ tag={} message={}", tag, message);
        try {
            String destination = topic + ":" + tag;
            rocketMQTemplate.convertAndSend(destination, message);
            log.info("DefaultMqProducer send MQ success destination={}", destination);
        } catch (Exception ex) {
            // TODO: 失败消息落库定时任务重试
            log.error("DefaultMqProducer send MQ Error tag={}", tag, ex);
        }
    }
}
