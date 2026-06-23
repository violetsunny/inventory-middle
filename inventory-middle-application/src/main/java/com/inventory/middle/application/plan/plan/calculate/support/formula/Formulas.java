package com.inventory.middle.application.plan.plan.calculate.support.formula;

import top.kdla.framework.validator.BaseAssert;
import com.inventory.middle.domain.plan.common.ex.Checker;
import com.inventory.middle.application.plan.plan.calculate.support.formula.indicator.Indicators;
import com.google.common.collect.Maps;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 公式装配
 *
 * @author Danny.Lee
 * @date 2021/9/28
 */
@Component
public class Formulas implements ApplicationListener<ContextRefreshedEvent> {

    private static final Map<String, Formula> FORMULAS = Maps.newHashMap();

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext context = event.getApplicationContext();
        Map<String, Formula> formulas = context.getBeansOfType(Formula.class);
        formulas.forEach((key, value) -> FORMULAS.put(value.indicatorCode(), value));
    }

    public static Formula formula(Indicators spec) {
        Formula formula = FORMULAS.get(spec.getIndicatorCode());
        BaseAssert.notNull(formula);
        return formula;
    }
}
