package com.inventory.middle.application.plan.plan.calculate.support.generate.processor;

import com.inventory.middle.application.plan.plan.calculate.bo.MaterialPlanInstanceBO;
import com.inventory.middle.application.plan.plan.calculate.bo.PlanInstanceBO;

/**
 * 物料计划实例后置处理器
 *
 * @author Danny.Lee
 * @date 2021/11/3
 */
public interface MaterialPlanInstancePostProcessor {

    /**
     * 物料计划实例后置处理
     * <pre>
     *     用于物料计划计算后，物料计划实例数据后置填充
     * </pre>
     *
     * @param planInstance         计划实例
     * @param materialPlanInstance 物料计划实例
     */
    void postProcess(PlanInstanceBO planInstance, MaterialPlanInstanceBO materialPlanInstance);
}
