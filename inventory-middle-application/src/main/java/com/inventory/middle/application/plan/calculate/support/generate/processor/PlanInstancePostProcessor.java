package com.inventory.middle.application.plan.calculate.support.generate.processor;

import com.inventory.middle.application.plan.calculate.bo.PlanInstanceBO;

/**
 * 计划实例后置处理器
 *
 * @author Danny.Lee
 * @date 2021/11/3
 */
public interface PlanInstancePostProcessor {

    /**
     * 计划实例后置处理
     * <pre>
     *     用于计划计算后，计划实例数据后置填充
     * </pre>
     *
     * @param planInstance 计划实例
     */
    void postProcess(PlanInstanceBO planInstance);
}
