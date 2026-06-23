package com.inventory.middle.application.plan.plan.calculate.support.formula;

import top.kdla.framework.validator.BaseAssert;
import com.inventory.middle.domain.plan.common.ex.Checker;
import com.inventory.middle.application.plan.plan.calculate.support.formula.factor.MaterialFactor;
import com.inventory.middle.application.plan.plan.calculate.support.formula.indicator.Indicator;
import com.inventory.middle.application.plan.plan.calculate.support.formula.indicator.IndicatorSpec;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.function.Function;

/**
 * 公式定义
 *
 * @author Danny.Lee
 * @date 2021/9/26
 */
public interface Formula extends Function<MaterialFactor, Indicator> {

    default String indicatorCode() {
        IndicatorSpec indicatorSpec = AnnotationUtils.findAnnotation(getClass(), IndicatorSpec.class);
        BaseAssert.notNull(indicatorSpec, "indicator spec annotation cannot be null");
        return indicatorSpec.value().getIndicatorCode();
    }
}
