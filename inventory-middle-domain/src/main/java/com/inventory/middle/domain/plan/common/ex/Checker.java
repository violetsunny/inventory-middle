package com.inventory.middle.domain.plan.common.ex;

import com.inventory.middle.domain.common.constants.ResponseCodeEnum;
import com.inventory.middle.domain.common.exception.BusinessException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 校验工具类 - 适配 inventory-middle 异常体系
 *
 * @author Danny.Lee
 * @date 2021/9/26
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Checker {

    public static <T> T notNull(T reference) {
        return notNull(reference, ResponseCodeEnum.PARAM_VALID_ERROR);
    }

    public static <T> T notNull(T reference, String errorMsg) {
        return notNull(reference, ResponseCodeEnum.PARAM_VALID_ERROR, errorMsg);
    }

    public static <T> T notNull(T reference, String errorMsg, Object... args) {
        return notNull(reference, ResponseCodeEnum.PARAM_VALID_ERROR, errorMsg, args);
    }

    public static <T> T notNull(T reference, ResponseCodeEnum responseCode) {
        if (null == reference) {
            throw new BusinessException(responseCode);
        }
        return reference;
    }

    public static <T> T notNull(T reference, ResponseCodeEnum responseCode, String errorMsg) {
        if (null == reference) {
            throw new BusinessException(responseCode, errorMsg);
        }
        return reference;
    }

    public static <T> T notNull(T reference, ResponseCodeEnum responseCode, String errorMsg, Object... args) {
        if (null == reference) {
            throw new BusinessException(responseCode, String.format(errorMsg, args));
        }
        return reference;
    }

    public static void checkState(boolean expression) {
        if (!expression) {
            throw new BusinessException(ResponseCodeEnum.PLAN_SYSTEM_ERROR);
        }
    }

    public static void checkState(boolean expression, String errorMsg) {
        if (!expression) {
            throw new BusinessException(errorMsg);
        }
    }

    public static void checkState(boolean expression, String errorMsg, Object... args) {
        if (!expression) {
            throw new BusinessException(String.format(errorMsg, args));
        }
    }

    public static void checkState(boolean expression, ResponseCodeEnum responseCode) {
        if (!expression) {
            throw new BusinessException(responseCode);
        }
    }

    public static void checkState(boolean expression, ResponseCodeEnum responseCode, String errorMsg) {
        if (!expression) {
            throw new BusinessException(responseCode, errorMsg);
        }
    }

    public static void checkState(boolean expression, ResponseCodeEnum responseCode, String errorMsg, Object... args) {
        if (!expression) {
            throw new BusinessException(responseCode, String.format(errorMsg, args));
        }
    }
}
