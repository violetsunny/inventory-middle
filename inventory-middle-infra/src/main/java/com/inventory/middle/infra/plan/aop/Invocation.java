package com.inventory.middle.infra.plan.aop;

import lombok.Data;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 调用上下文
 * Created on 2021/11/24.
 *
 * @author Danny.Lee
 */
@Data
public class Invocation {

    /** 调用类 */
    private Class<?> classSpec;

    /** 调用方法 */
    private Method methodSpec;

    /** 方法参数 */
    private Object[] parameters;

    /** 调用结果 */
    private Object result;

    /** 异常信息 */
    private Throwable error;

    /** 扩展字段 */
    private Map<String, Object> extAttrs;
}
