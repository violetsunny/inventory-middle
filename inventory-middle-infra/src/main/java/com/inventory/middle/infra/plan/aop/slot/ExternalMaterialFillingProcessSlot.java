package com.inventory.middle.infra.plan.aop.slot;

import com.google.common.collect.Maps;
import com.inventory.middle.client.plan.dto.ExternalMaterialSupport;
import com.inventory.middle.infra.plan.aop.Invocation;
import com.inventory.middle.infra.plan.aop.anno.ExternalMaterialProcess;
import com.inventory.middle.infra.plan.aop.extension.ExternalMaterialFillingProcessor;
import com.inventory.middle.infra.plan.aop.extension.ExtensionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * 外部物料编码填充处理节点
 *
 * @author Danny.Lee
 * @date 2022/5/11
 */
@Slf4j
public class ExternalMaterialFillingProcessSlot extends AbstractProcessSlot {

    private static final ExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();

    private final Map<String, Expression> expressionCache = Maps.newHashMap();

    @Override
    public void exit(Invocation invocation) {
        ExternalMaterialProcess extProcessAnnotation =
                AnnotationUtils.findAnnotation(invocation.getMethodSpec(), ExternalMaterialProcess.class);

        Optional.ofNullable(extProcessAnnotation).ifPresent(annotation ->
                this.processExternalMaterial(invocation, annotation.patterns()));

        this.fireExit(invocation);
    }

    private void processExternalMaterial(Invocation invocation, String[] patterns) {
        Arrays.stream(patterns).parallel().forEach(pattern -> {
            Expression expression = expressionCache.get(pattern);
            if (null == expression) {
                synchronized (expressionCache) {
                    expression = expressionCache.get(pattern);
                    if (null == expression) {
                        expression = EXPRESSION_PARSER.parseExpression(pattern);
                        expressionCache.put(pattern, expression);
                    }
                }
            }
            this.fillExternalMaterial(expression.getValue(invocation.getResult()));
        });
    }

    private void fillExternalMaterial(Object value) {
        if (null == value) {
            return;
        }
        if (value instanceof Collection) {
            for (Object o : (Collection<?>) value) {
                this.singleFillExternalMaterial(o);
            }
        } else {
            this.singleFillExternalMaterial(value);
        }
    }

    private void singleFillExternalMaterial(Object value) {
        if (value instanceof ExternalMaterialSupport) {
            ExternalMaterialSupport data = (ExternalMaterialSupport) value;
            ExternalMaterialFillingProcessor processor =
                    ExtensionManager.findExtension(ExternalMaterialFillingProcessor.class, data.getTenantId());
            processor.apply(data);
        }
    }
}
