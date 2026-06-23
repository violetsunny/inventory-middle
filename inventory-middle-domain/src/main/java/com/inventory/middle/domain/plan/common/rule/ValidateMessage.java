package com.inventory.middle.domain.plan.common.rule;

import lombok.Builder;

/**
 * 验证载体
 *
 * @author peisheng.wang
 * @date 2021/9/27
 */
@Builder
public class ValidateMessage<T, E> {

    /** 业务请求类 */
    private T t;

    /** 业务返回类 */
    private E e;

    /** 状态 */
    private boolean success;

    /** 返回码 */
    private String code;

    /** 返回消息 */
    private String message;

    public T getT() {
        return t;
    }

    public ValidateMessage<T, E> setT(T t) {
        this.t = t;
        return this;
    }

    public E getE() {
        return e;
    }

    public ValidateMessage<T, E> setE(E e) {
        this.e = e;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ValidateMessage<T, E> setSuccess(boolean success) {
        this.success = success;
        return this;
    }
}
