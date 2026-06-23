package com.inventory.middle.domain.plan.common.ex;

import com.inventory.middle.domain.common.exception.BusinessException;

/**
 * 计划域运行时异常
 * <p>
 * 迁移自 com.enn.plan.management.core.common.ex.SpmException
 * 适配 inventory-middle 异常体系，继承 BusinessException (kdla BizException)
 * </p>
 */
public class SpmException extends BusinessException {

    public SpmException(String code, String message) {
        super(code, message);
    }

    public SpmException(String message) {
        super("500", message);
    }
}
