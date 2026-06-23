package com.inventory.middle.domain.plan.common.rule;

/**
 * 验证器接口
 *
 * @author peisheng.wang
 * @date 2021/10/2
 */
public interface IValidator {

    /**
     * 验证
     *
     * @param message 验证消息载体
     * @return 验证结果
     */
    ValidateMessage doValidate(ValidateMessage message);
}
