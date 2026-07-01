package com.inventory.middle.domain.plan.common.ex;

import com.inventory.middle.domain.common.exception.BusinessException;
import com.inventory.middle.domain.common.constants.ResponseCodeEnum;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.text.MessageFormat;

/**
 * 计划管理异常工具类 - 适配 inventory-middle 异常体系
 * 原 scm-plan-management 中的 Ex 工具类，抛出 BusinessException（继承 kdla BizException）。
 *
 * @author Danny.Lee (migrated)
 * @date 2021/9/26
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Ex {

    public static BusinessException of(ResponseCodeEnum errorCode) {
        return of(errorCode.getCode(), errorCode.getDesc());
    }

    public static BusinessException of(String code, String message) {
        return of(code, message, new Object[0]);
    }

    public static BusinessException of(Throwable t, String code, String message) {
        return of(t, code, message, new Object[0]);
    }

    public static BusinessException of(String code, String message, Object... formatArgs) {
        return new BusinessException(code, MessageFormat.format(message, formatArgs));
    }

    public static BusinessException of(Throwable t, String code, String message, Object... formatArgs) {
        return new BusinessException(code, MessageFormat.format(message, formatArgs));
    }
}
