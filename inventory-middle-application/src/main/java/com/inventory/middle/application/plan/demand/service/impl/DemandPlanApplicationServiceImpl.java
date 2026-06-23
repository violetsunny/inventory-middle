package com.inventory.middle.application.plan.demand.service.impl;

import com.inventory.middle.application.plan.demand.bo.*;
import com.inventory.middle.application.plan.demand.convertor.*;
import com.inventory.middle.application.plan.demand.service.DemandPlanApplicationService;
import com.inventory.middle.application.plan.demand.service.DemandPlanService;
import com.inventory.middle.client.plan.demand.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.SingleResponse;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 需求计划应用服务实现
 * 适配 RPC 接口到内部 Service
 */
@Service
@Slf4j
public class DemandPlanApplicationServiceImpl implements DemandPlanApplicationService {

    @Resource
    private DemandPlanService demandPlanService;

    @Override
    public SingleResponse createDemandPlan(DemandPlanCreateReqDTO request) {
        DemandPlanBO demandPlanBO = DemandPlanCreateReqConverter.INSTANCE.toBO(request);
        boolean result = demandPlanService.createDemandPlan(demandPlanBO);
        return result ? SingleResponse.buildSuccess() : SingleResponse.buildFailure("CREATE_FAILED", "创建需求计划失败");
    }

    @Override
    public SingleResponse updateDemandPlan(DemandPlanUpdateReqDTO request) {
        DemandPlanBO demandPlanBO = DemandPlanUpdateReqConverter.INSTANCE.toBO(request);
        boolean result = demandPlanService.updateDemandPlan(demandPlanBO);
        return result ? SingleResponse.buildSuccess() : SingleResponse.buildFailure("UPDATE_FAILED", "更新需求计划失败");
    }

    @Override
    public SingleResponse<DemandPlanMaterialBatchCreateResDTO> createDemandPlanMaterialPeriod(DemandPlanMaterialBatchCreateReqDTO request) {
        DemandPlanMaterialBatchCreateReqBO createBO = DemandPlanMaterialBatchCreateReqConverter.INSTANCE.toBO(request);
        DemandPlanMaterialBatchCreateResBO resBO = demandPlanService.createDemandPlanMaterialPeriod(createBO);
        DemandPlanMaterialBatchCreateResDTO resDTO = DemandPlanMaterialBatchCreateResConverter.INSTANCE.toDTO(resBO);
        return SingleResponse.of(resDTO);
    }

    @Override
    public SingleResponse updateDemandPlanStatus(DemandPlanUpdateStatusDTO request) {
        DemandPlanUpdateStatusBO statusBO = DemandPlanUpdateStatusConverter.INSTANCE.toBO(request);
        boolean result = demandPlanService.updateDemandPlanStatus(statusBO);
        return result ? SingleResponse.buildSuccess() : SingleResponse.buildFailure("UPDATE_STATUS_FAILED", "更新状态失败");
    }

    @Override
    public SingleResponse cancelDemandPlanMaterial(CancelDemandPlanMaterialReqDTO request) {
        CancelDemandPlanMaterialReqBO reqBO = CancelDemandPlanMaterialReqConverter.INSTANCE.toBO(request);
        boolean result = demandPlanService.cancelDemandPlanMaterial(reqBO);
        return result ? SingleResponse.buildSuccess() : SingleResponse.buildFailure("CANCEL_FAILED", "剔除物料失败");
    }

    @Override
    public SingleResponse modifyDemandPlanMaterialAmount(ModifyDemandPlanMaterialAmountReqDTO request) {
        ModifyDemandPlanMaterialAmountReqBO reqBO = ModifyDemandPlanMaterialAmountReqConverter.INSTANCE.toBO(request);
        boolean result = demandPlanService.modifyDemandPlanMaterialAmount(reqBO);
        return result ? SingleResponse.buildSuccess() : SingleResponse.buildFailure("MODIFY_FAILED", "修改物料数量失败");
    }

    @Override
    @Deprecated
    public List<String> exportDemandPlanTemplate(Long demandPlanId, String tenantId) {
        return demandPlanService.exportDemandPlanTemplate(demandPlanId, tenantId);
    }

    @Override
    public SingleResponse<ArrayList<String>> exportTemplate(Long demandPlanId, String tenantId) {
        List<String> templateList = demandPlanService.exportDemandPlanTemplate(demandPlanId, tenantId);
        return SingleResponse.of(new ArrayList<>(templateList));
    }

    @Override
    public PageResponse<DemandPlanSelectResDTO> selectDemandPlanByPage(DemandPlanSelectReqDTO reqDTO, int pageNum, int pageSize) {
        DemandPlanSelectReqBO reqBO = FacadeDemandPlanSelectConverter.INSTANCE.toReqBO(reqDTO);
        PageResponse<DemandPlanSelectResBO> pagedResponse = 
            demandPlanService.selectDemandPlanByPage(reqBO, pageNum, pageSize);
        
        List<DemandPlanSelectResDTO> resDTOList = FacadeDemandPlanSelectConverter.INSTANCE.toResDTOList((List<DemandPlanSelectResBO>) pagedResponse.getData());
        return PageResponse.of(resDTOList, pagedResponse.getTotalCount(), pageSize, pageNum);
    }

    @Override
    public SingleResponse<DemandPlanVersionSelectResDTO> selectByLogicalPlantNos(DemandPlanVersionSelectReqDTO reqDTO) {
        DemandPlanVersionSelectReqBO reqBO = FacadeDemandPlanVersionConverter.INSTANCE.toReqBO(reqDTO);
        DemandPlanVersionSelectResBO resBO = demandPlanService.selectByLogicalPlantNos(reqBO);
        DemandPlanVersionSelectResDTO resDTO = FacadeDemandPlanVersionConverter.INSTANCE.toResDTO(resBO);
        return SingleResponse.of(resDTO);
    }

    @Override
    public SingleResponse<DemandPlanDetailSelectResDTO> selectDemandPlanDetail(DemandPlanDetailSelectReqDTO reqDTO) {
        DemandPlanDetailSelectReqBO reqBO = FacadeDemandPlanDetailConverter.INSTANCE.toReqBO(reqDTO);
        DemandPlanDetailSelectResBO resBO = demandPlanService.selectDemandPlanDetail(reqBO);
        DemandPlanDetailSelectResDTO resDTO = FacadeDemandPlanDetailConverter.INSTANCE.toResDTO(resBO);
        return SingleResponse.of(resDTO);
    }
}
