package com.inventory.middle.application.plan.plan.calculate.service;

import com.inventory.middle.application.plan.plan.calculate.bo.MaterialPlanInstanceBomNodeBO;
import com.inventory.middle.application.plan.plan.calculate.bo.MaterialPlanInstanceWithPlanBO;
import com.inventory.middle.application.plan.plan.calculate.bo.PlanGenerateRequestBO;
import com.inventory.middle.application.plan.plan.calculate.bo.PlanInstanceBO;
import com.inventory.middle.application.plan.plan.calculate.bo.report.MaterialPlanReportBO;
import com.inventory.middle.application.plan.plan.calculate.support.report.collector.CollectType;
import com.inventory.middle.infra.plan.persistence.condition.plan.MaterialPlanInstanceCondition;
import top.kdla.framework.dto.PagedSingleResponse;

/**
 * 计划子域产出服务
 *
 * @author Danny.Lee
 * @date 2021/9/26
 */
public interface PlanGenerateService {

    /**
     * 计划产出
     *
     * @param planGenerateRequest 计划产出请求
     * @return 计划执行实例
     */
    PlanInstanceBO generate(PlanGenerateRequestBO planGenerateRequest);

    /**
     * 分页查询物料执行实例
     *
     * @param condition 查询条件
     * @return 物料执行实例
     */
    PagedSingleResponse<MaterialPlanInstanceWithPlanBO> pageQueryMaterialPlanInstances(MaterialPlanInstanceCondition condition);

    /**
     * 根据物料执行实例查询物料计划报表
     *
     * @param materialInstanceId 物料执行实例id
     * @param tenantId           租户id
     * @param collectType        汇总类型
     * @return 物料计划报表
     */
    MaterialPlanReportBO queryMaterialPlanReportByMaterialInstance(Long materialInstanceId, String tenantId, CollectType collectType);


    /**
     * 根据物料执行实例构建BomTree
     *
     * @param materialInstanceId 物料执行实例id
     * @param tenantId           租户id
     * @return 物料计划报表Tree
     */
    MaterialPlanInstanceBomNodeBO renderMaterialBomTree(Long materialInstanceId, String tenantId);

}
