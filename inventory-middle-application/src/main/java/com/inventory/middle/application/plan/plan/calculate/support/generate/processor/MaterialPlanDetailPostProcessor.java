package com.inventory.middle.application.plan.plan.calculate.support.generate.processor;

import com.inventory.middle.application.plan.plan.calculate.bo.MaterialPlanDetailBO;
import com.inventory.middle.application.plan.plan.calculate.bo.MaterialPlanInstanceBO;

/**
 * 物料计划详情后置处理器
 *
 * @author Danny.Lee
 * @date 2021/11/3
 */
public interface MaterialPlanDetailPostProcessor {

    /**
     * 物料计划详情后置处理
     * <pre>
     *     用于物料计划详情计算后，物料计划详情数据后置填充
     * </pre>
     *
     * @param materialPlanInstance 物料计划实例
     * @param detail               物料计划详情
     */
    void postProcess(MaterialPlanInstanceBO materialPlanInstance, MaterialPlanDetailBO detail);
}
