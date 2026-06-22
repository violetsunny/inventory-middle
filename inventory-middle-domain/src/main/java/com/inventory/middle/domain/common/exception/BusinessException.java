package com.inventory.middle.domain.common.exception;

import com.inventory.middle.domain.common.constants.ResponseCodeEnum;
import top.kdla.framework.exception.BizException;

/**
 * 业务异常 — 继承 KDLA BizException，确保被 @CatchAndLog 切面正确识别。
 * CatchLogAspect 会将 BizException 以 warn 级别记录并返回业务错误码，
 * 而非降级为 UNKNOWN_ERROR + error 日志。
 */
public class BusinessException extends BizException {

    public BusinessException(ResponseCodeEnum responseCodeEnum) {
        super(responseCodeEnum == null
                        ? ResponseCodeEnum.FAILED.getCode()
                        : responseCodeEnum.getCode(),
                responseCodeEnum == null
                        ? ResponseCodeEnum.FAILED.getDesc()
                        : responseCodeEnum.getDesc());
    }

    public BusinessException(ResponseCodeEnum responseCodeEnum, String message) {
        super(responseCodeEnum == null
                        ? ResponseCodeEnum.FAILED.getCode()
                        : responseCodeEnum.getCode(),
                (responseCodeEnum == null
                        ? ResponseCodeEnum.FAILED.getDesc()
                        : responseCodeEnum.getDesc()) + "," + message);
    }

    public BusinessException(String msg) {
        super(ResponseCodeEnum.FAILED.getCode(), msg);
    }

    public BusinessException(String code, String msg) {
        super(code, msg);
    }

    public BusinessException(String code, String errorInfo, Object... args) {
        super(code, String.format(errorInfo, args));
    }

    public BusinessException(String msg, Throwable e) {
        super(ResponseCodeEnum.FAILED.getCode(), msg, e);
    }

    /**
     * 兼容旧代码 e.getMsg() 调用，委托给标准 getMessage()。
     * BaseException 通过 super(message) 保存 message，可直接取用。
     */
    public String getMsg() {
        return getMessage();
    }
}
