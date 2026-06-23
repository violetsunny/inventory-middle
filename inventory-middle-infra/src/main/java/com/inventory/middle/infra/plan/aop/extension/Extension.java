package com.inventory.middle.infra.plan.aop.extension;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 支撑域适配器注解
 *
 * @author Danny.Lee
 * @date 2022/4/15
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Extension {

    /**
     * @return 租户id
     */
    String[] value();
}
