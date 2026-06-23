package com.inventory.middle.application.plan.plan.calculate.support.formula;

import top.kdla.framework.validator.BaseAssert;
import com.inventory.middle.domain.plan.common.enums.ResponseCodeEnum;
import com.inventory.middle.domain.plan.common.ex.Checker;
import com.inventory.middle.domain.plan.common.ex.Ex;
import com.inventory.middle.application.plan.plan.calculate.support.formula.factor.MaterialFactor;
import com.inventory.middle.application.plan.plan.calculate.support.formula.indicator.Indicator;
import com.inventory.middle.application.plan.plan.calculate.support.formula.indicator.IndicatorSpec;
import com.inventory.middle.application.plan.plan.calculate.support.formula.indicator.Indicators;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

/**
 * 公式计算拦截切面
 * 使用上下文缓存计算过程中的指标值，避免重复计算
 *
 * @author Danny.Lee
 * @date 2021/9/28
 */
@Slf4j
@Aspect
@Component
public class FormulaAspect {

    @Around(value = "execution(* com.inventory.middle.application.plan.plan.calculate.support.formula.Formula.apply(..))")
    public Object process(ProceedingJoinPoint point) {
        try {
            // 获取指标信息
            Class<?> formulaClass = point.getTarget().getClass();
            Indicators indicator = this.findIndicator(formulaClass);
            MaterialFactor factor = this.findFactor(point);
            FormulaContext context = FormulaContext.get();
            if (null == context) {
                // 若不存在上下文，直接返回公式结果
                return point.proceed();
            }
            Indicator value = context.getIndicator(factor, factor.getPlanDate(), indicator.getIndicatorCode());
            if (null != value) {
                return value;
            }
            value = (Indicator) point.proceed();
            context.addIndicator(factor, factor.getPlanDate(), indicator.getIndicatorCode(), value);
            return value;
        } catch (Throwable t) {
            log.error("formula calculate error|formula={}, factor={}",
                    point.getTarget().getClass().getSimpleName(), point.getArgs()[0], t);
            throw Ex.of(t, ResponseCodeEnum.SYSTEM_ERROR.getCode(), "formula calculate error");
        }
    }

    private MaterialFactor findFactor(ProceedingJoinPoint point) {
        Object[] arguments = point.getArgs();
        return (MaterialFactor) arguments[0];
    }

    private Indicators findIndicator(Class<?> formulaClass) {
        IndicatorSpec indicatorSpec = AnnotationUtils.findAnnotation(formulaClass, IndicatorSpec.class);
        BaseAssert.notNull(indicatorSpec, "formula={} without indicator annotation", formulaClass.getSimpleName());
        return indicatorSpec.value();
    }

}
