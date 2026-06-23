package com.inventory.middle.client.plan.plan.service;

import com.inventory.middle.client.plan.plan.dto.ChangePlanStatusDTO;
import com.inventory.middle.client.plan.plan.dto.PlanDTO;
import com.inventory.middle.client.plan.plan.dto.PlanMaterialBatchCreateReqDTO;
import com.inventory.middle.client.plan.plan.dto.PlanMaterialBatchCreateResDTO;
import com.inventory.middle.client.plan.plan.dto.PlanMaterialBatchDeleteReqDTO;
import com.inventory.middle.client.plan.plan.dto.PlanMaterialBatchDeleteResDTO;
import com.inventory.middle.client.plan.plan.dto.PlanMaterialDTO;
import com.inventory.middle.client.plan.plan.dto.PlanMaterialParamBatchCreateReqDTO;
import com.inventory.middle.client.plan.plan.dto.PlanMaterialParamBatchCreateResDTO;
import com.inventory.middle.client.plan.plan.dto.PlanMaterialParamPageReqDTO;
import com.inventory.middle.client.plan.plan.dto.PlanMaterialParamQueryReqDTO;
import com.inventory.middle.client.plan.plan.dto.PlanMaterialParameterDTO;
import com.inventory.middle.client.plan.plan.dto.PlanPageReqDTO;
import com.inventory.middle.client.plan.plan.dto.PlanParamEnumListResDTO;
import com.inventory.middle.client.plan.plan.dto.PlanParamMaterialEnumListResDTO;
import com.inventory.middle.client.plan.plan.dto.PlanTransferLogicalPlantsResDTO;
import com.inventory.middle.client.plan.plan.dto.QueryPlanTransferLogicalPlantReqDTO;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.SingleResponse;

import java.util.ArrayList;

/**
 * 计划配置 RPC 接口
 */
public interface PlanConfigRpcService {

    /** 批量创建物料计划参数配置 */
    SingleResponse<PlanMaterialParamBatchCreateResDTO> batchCreatePlanMaterialParam(PlanMaterialParamBatchCreateReqDTO dto) throws Exception;

    /** 更新物料计划参数配置 */
    SingleResponse updatePlanMaterialParam(PlanMaterialParameterDTO dto);

    /** 查询物料计划参数配置 */
    SingleResponse<PlanMaterialParameterDTO> queryByMaterialCodeAndLogicalPlantNo(PlanMaterialParamQueryReqDTO dto);

    /** 分页查询物料计划参数 */
    PageResponse<PlanMaterialParameterDTO> pageQueryPlanMaterialParam(PlanMaterialParamPageReqDTO dto);

    /** 创建计划方案 */
    SingleResponse<Boolean> createPlan(PlanDTO dto);

    /** 更新计划方案 */
    SingleResponse<Boolean> updatePlan(PlanDTO dto);

    /** 根据Id查询计划方案 */
    SingleResponse<PlanDTO> queryPlanById(Long id, String tenantId);

    /** 分页查询计划方案 */
    PageResponse<PlanDTO> pageQueryPlan(PlanPageReqDTO bo);

    /** 批量创建计划方案物料 */
    SingleResponse<PlanMaterialBatchCreateResDTO> batchCreatePlanMaterial(PlanMaterialBatchCreateReqDTO dto) throws Exception;

    /** 批量删除计划方案物料 */
    SingleResponse<PlanMaterialBatchDeleteResDTO> batchDeletePlanMaterial(PlanMaterialBatchDeleteReqDTO reqDTO);

    /** 根据计划方案ID查询物料 */
    SingleResponse<ArrayList<PlanMaterialDTO>> queryPlanMaterialByPlanId(Long planId, String tenantId);

    /** 获取计划参数枚举类列表 */
    SingleResponse<PlanParamEnumListResDTO> getPlanParamEnumList();

    /** 获取计划参数枚举类列表（物料维度） */
    SingleResponse<PlanParamMaterialEnumListResDTO> getPlanMaterialParamEnumList();

    /** 根据计划方案ID更新计划状态 */
    SingleResponse<Boolean> changeStatus(ChangePlanStatusDTO dto);

    /** 查询计划逻辑仓 */
    SingleResponse<PlanTransferLogicalPlantsResDTO> queryPlanTransferLogicalPlants(QueryPlanTransferLogicalPlantReqDTO reqDTO);
}
