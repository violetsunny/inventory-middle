package com.inventory.middle.interfaces.consumer.validator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 物料凭证 MQ 消息校验器
 * 迁移自: com.enn.inventory.center.processor.validator.MaterialDcoMqValidator
 * <p>
 * 封装 JSR-303 Bean Validation，供各 MQ Consumer 复用。
 */
@Slf4j
@Component
public class MaterialDocMqValidator {

    private static final Validator VALIDATOR =
            Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * 校验 MQ 消息对象。
     * 校验不通过时抛出 {@link IllegalArgumentException}，消息会被 RocketMQ 重试。
     *
     * @param message 待校验的消息对象
     * @param <T>     消息类型
     * @throws IllegalArgumentException 包含所有校验错误信息
     */
    public <T> void validate(T message) {
        Set<ConstraintViolation<T>> violations = VALIDATOR.validate(message);
        if (!violations.isEmpty()) {
            String errors = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining("; "));
            log.error("MaterialDocMqValidator: validation failed - {}", errors);
            throw new IllegalArgumentException("MQ message validation failed: " + errors);
        }
    }
}
