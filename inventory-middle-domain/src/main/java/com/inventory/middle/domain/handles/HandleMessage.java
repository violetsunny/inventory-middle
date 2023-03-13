/**
 * LY.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.inventory.middle.domain.handles;

/**
 * 处理器消息载体
 * @author kll
 * @version $Id: Message.java, Exp $
 */
public class HandleMessage<T, E> {
    /** 业务请求类 */
    private T       t;
    /** 业务返回类 */
    private E       e;
    /** 状态 */
    private boolean success;

    public T getT() {
        return t;
    }

    public HandleMessage<T, E> setT(T t) {
        this.t = t;
        return this;
    }

    public E getE() {
        return e;
    }

    public HandleMessage<T, E> setE(E e) {
        this.e = e;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
