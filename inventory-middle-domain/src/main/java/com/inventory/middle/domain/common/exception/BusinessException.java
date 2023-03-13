package com.inventory.middle.domain.common.exception;

import com.inventory.middle.domain.common.constants.ResponseCodeEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessException  extends RuntimeException {

    private String code;

    private String msg;

    public BusinessException(ResponseCodeEnum responseCodeEnum) {
        if (responseCodeEnum == null) {
            responseCodeEnum = ResponseCodeEnum.FAILED;
        }
        this.code = responseCodeEnum.getCode();
        this.msg = responseCodeEnum.getDesc();
    }

    public BusinessException(ResponseCodeEnum responseCodeEnum, String message) {
        if (responseCodeEnum == null) {
            responseCodeEnum = ResponseCodeEnum.FAILED;
        }
        this.code = responseCodeEnum.getCode();
        this.msg = responseCodeEnum.getDesc() + "," + message;
    }

    public BusinessException(String msg) {
        super(msg);
        this.code = ResponseCodeEnum.FAILED.getCode();
        this.msg = msg;
    }

    public BusinessException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public BusinessException(String code, String errorInfo, Object... args){
        super(String.format(errorInfo, args));
        this.code = code;
        this.msg = String.format(errorInfo, args);
    }

    public BusinessException(String msg, Throwable e) {
        super(msg, e);
        this.code = ResponseCodeEnum.FAILED.getCode();
        this.msg = msg;
    }


    @Override
    public String toString() {
        return "BusinessException{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
