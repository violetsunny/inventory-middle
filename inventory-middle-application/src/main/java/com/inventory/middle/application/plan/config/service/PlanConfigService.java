package com.inventory.middle.application.plan.config.service;

import com.inventory.middle.domain.plan.common.bo.MaterialBO;
import com.inventory.middle.application.plan.config.bo.*;
import top.kdla.framework.dto.PagedSingleResponse;

import java.util.List;

/**
 * 计划子域配置服务
 *
 * @author Danny.Lee
 * @date 2021/9/26
 */
public interface PlanConfigService {

    /**
     * 批量新增物料计划参数
     *
     * @param bo
     * @return
     * @throws Exception
     */
    PlanMaterialParamBatchCreateResBO batchCreatePlanMaterialParam(PlanMaterialParamBatchCreateReqBO bo);

    /**
     * 更新物料计划参数
     *
     * @param bo
     * @return
     */
    Boolean updatePlanMaterialParam(PlanMaterialParameterBO bo);

    /**
     * 根据逻辑仓编码物料code查询物料计划参数
     *
     * @param bo
     * @return
     */
    PlanMaterialParameterBO queryByMaterialCodeAndLogicalPlantNo(PlanMaterialParamQueryReqBO bo);

    /**
     * 分页查询物料计划参数
     *
     * @param bo
     * @return
     */
    PagedSingleResponse<PlanMaterialParameterBO> pageQueryPlanMaterialParam(PlanMaterialParamPageReqBO bo);

    /**
     * 创建计划方案
     *
     * @param bo
     * @return
     */
    Boolean createPlan(PlanBO bo);

    /**
     * 更新计划方案
     *
     * @param bo
     * @return
     */
    Boolean updatePlan(PlanBO bo);

    /**
     * 根据Id查询计划方案
     *
     * @param id
     * @return
     */
    PlanBO queryPlanById(Long id, String tenantId);

    /**
     * 根据执行类型查询计划方案集合
     *
     * @param bo
     * @return
     */
    List<PlanBO> queryPlanByType(PlanQueryByTypeReqBO bo);

    /**
     * 分页查询计划方案
     *
     * @param bo
     * @return
     */
    PagedSingleResponse<PlanBO> pageQueryPlan(PlanPageReqBO bo);

    /**
     * 批量新增计划物料
     *
     * @param bo
     * @return
     * @throws Exception
     */
    PlanMaterialBatchCreateResBO batchCreatePlanMaterial(PlanMaterialBatchCreateReqBO bo) throws Exception;

    /**
     * 批量删除计划物料
     *
     * @param boList
     * @return
     */
    PlanMaterialBatchDeleteResBO batchDeletePlanMaterial(PlanMaterialBatchDeleteReqBO bo);

    /**
     * 根据计划Id去查询计划物料
     *
     * @param planId
     * @return
     */
    List<PlanMaterialBO> queryPlanMaterialByPlanId(Long planId, String tenantId);


    /**
     * 根据启用的计划方案查询覆盖物料
     *
     * @param plan  计划方案
     * @return 物料信息
     */
    List<MaterialBO> queryMaterialsByPlan(PlanBO plan);

    /**
     * 根据物料信息查找启用的计划方案
     *
     * @param material 物料编码
     * @return 计划方案
     */
    PlanBO queryPlanByMaterial(MaterialBO material);


    /**
     * 获取计划参数枚举类
     *
     * @return map
     */
    PlanParamEnumResBO getPlanParamEnumList();


    /**
     * 根据计划Id更新计划状态
     *
     * @param bo bo
     * @return boolean
     */
    Boolean changeStatus(ChangeStatusPlanBO bo);

    /**
     * 获取物料计划类型枚举列表
     *
     * @return map
     */
    PlanMaterialParamEnumResBO getPlanMaterialParamEnumList();

    /**
     * 查询计划物料参数可调拨逻辑仓
     * @param reqBO reqBO
     * @return bo
     */
    PlanTransferLogicalPlantsResBO queryPlanTransferLogicalPlants(QueryPlanTransferLogicalPlantsReqBO reqBO);
}
