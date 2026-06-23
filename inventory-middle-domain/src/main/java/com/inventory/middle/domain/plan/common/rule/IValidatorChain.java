package com.inventory.middle.domain.plan.common.rule;

/**
 * 验证器链接口
 *
 * @author peisheng.wang
 * @date 2021/9/27
 */
public interface IValidatorChain<T, E> {

    /**
     * 处理
     *
     * @param originalMessage 原始消息
     * @return 处理结果
     */
    ValidateMessage<T, E> doProcess(ValidateMessage<T, E> originalMessage);
}
