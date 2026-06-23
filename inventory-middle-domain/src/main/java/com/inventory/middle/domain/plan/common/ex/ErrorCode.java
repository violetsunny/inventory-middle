package com.inventory.middle.domain.plan.common.ex;

/**
 * 错误码接口
 * <p>
 * 迁移自 com.enn.plan.management.core.common.ex.ErrorCode
 * </p>
 */
public interface ErrorCode {

    /**
     * 错误码
     */
    String errCode();

    /**
     * 错误信息（技术描述）
     */
    String errMessage();

    /**
     * 用户提示（友好描述），可为 null
     */
    default String userTip() {
        return null;
    }
}
