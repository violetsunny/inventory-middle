package com.inventory.middle.application.plan.plan.calculate.support.formula.indicator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 指标注解
 *
 * @author Danny.Lee
 * @date 2021/10/6
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface IndicatorSpec {

    Indicators value();
}
