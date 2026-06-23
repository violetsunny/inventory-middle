package com.inventory.middle.client.plan.demand.service;

import com.inventory.middle.client.plan.demand.dto.CancelDemandPlanMaterialReqDTO;
import com.inventory.middle.client.plan.demand.dto.DemandPlanCreateReqDTO;
import com.inventory.middle.client.plan.demand.dto.DemandPlanDetailSelectReqDTO;
import com.inventory.middle.client.plan.demand.dto.DemandPlanDetailSelectResDTO;
import com.inventory.middle.client.plan.demand.dto.DemandPlanMaterialBatchCreateReqDTO;
import com.inventory.middle.client.plan.demand.dto.DemandPlanMaterialBatchCreateResDTO;
import com.inventory.middle.client.plan.demand.dto.DemandPlanSelectReqDTO;
import com.inventory.middle.client.plan.demand.dto.DemandPlanSelectResDTO;
import com.inventory.middle.client.plan.demand.dto.DemandPlanUpdateReqDTO;
import com.inventory.middle.client.plan.demand.dto.DemandPlanUpdateStatusDTO;
import com.inventory.middle.client.plan.demand.dto.DemandPlanVersionSelectReqDTO;
import com.inventory.middle.client.plan.demand.dto.DemandPlanVersionSelectResDTO;
import com.inventory.middle.client.plan.demand.dto.ModifyDemandPlanMaterialAmountReqDTO;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.SingleResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * 需求计划 RPC 接口
 */
public interface DemandPlanRpcService {

    /** 保存需求计划基本信息 */
    SingleResponse createDemandPlan(DemandPlanCreateReqDTO request);

    /** 修改计划需求 */
    SingleResponse updateDemandPlan(DemandPlanUpdateReqDTO request);

    /** 导入需求物料 */
    SingleResponse<DemandPlanMaterialBatchCreateResDTO> createDemandPlanMaterialPeriod(DemandPlanMaterialBatchCreateReqDTO request);

    /** 更新需求计划状态 */
    SingleResponse updateDemandPlanStatus(DemandPlanUpdateStatusDTO request);

    /** 剔除物料 */
    SingleResponse cancelDemandPlanMaterial(CancelDemandPlanMaterialReqDTO request);

    /** 修改物料数量 */
    SingleResponse modifyDemandPlanMaterialAmount(ModifyDemandPlanMaterialAmountReqDTO request);

    /** 导出模板（已废弃） */
    @Deprecated
    List<String> exportDemandPlanTemplate(Long demandPlanId, String tenantId);

    /** 导出模板 */
    SingleResponse<ArrayList<String>> exportTemplate(Long demandPlanId, String tenantId);

    /** 分页查询 */
    PageResponse<DemandPlanSelectResDTO> selectDemandPlanByPage(DemandPlanSelectReqDTO reqDTO, int pageNum, int pageSize);

    /** 需求计划文件查询 */
    SingleResponse<DemandPlanVersionSelectResDTO> selectByLogicalPlantNos(DemandPlanVersionSelectReqDTO reqDTO);

    /** 需求计划详情 */
    SingleResponse<DemandPlanDetailSelectResDTO> selectDemandPlanDetail(DemandPlanDetailSelectReqDTO reqDTO);
}
