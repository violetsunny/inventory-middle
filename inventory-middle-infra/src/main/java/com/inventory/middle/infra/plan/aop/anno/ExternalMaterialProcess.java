package com.inventory.middle.infra.plan.aop.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 模型填充外部物料编码匹配规则
 *
 * @author Danny.Lee
 * @date 2022/5/12
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExternalMaterialProcess {

    /**
     * 模型填充外部物料编码匹配规则
     *
     * @return Spel表达式匹配规则
     */
    String[] patterns();
}
