package com.inventory.middle.interfaces.web.plan.plan;

import com.inventory.middle.application.plan.plan.config.bo.*;
import com.inventory.middle.interfaces.web.plan.dto.*;
import com.inventory.middle.interfaces.web.plan.vo.*;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import com.inventory.middle.interfaces.support.UserContext;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 计划配置转换器
 * @date 2021/9/26 19:32
 */
public class PlanConfigConverter {

    private static final String SAFETY_STOCK_FACTOR_DEMAND_STD ="demandStd";
    private static final String SAFETY_STOCK_FACTOR_SAFETY_COEFFICIENT ="safetyCoefficient";
    private static final String SAFETY_STOCK_FACTOR_GUARANTEE_INTERVAL ="guaranteeInterval";

    public static PlanMaterialParamImportResVO convertPlanMaterialParamImportResBO2VO(PlanMaterialParamImportResBO resultBO) {
        if (resultBO == null) {
            return null;
        }
        PlanMaterialParamImportResVO planMaterialParamImportResVO = new PlanMaterialParamImportResVO();
        planMaterialParamImportResVO.setTotalCount(resultBO.getTotalCount());
        planMaterialParamImportResVO.setFailedCount(resultBO.getFailedCount());
        planMaterialParamImportResVO.setFailedDetails(resultBO.getFailedDetails().stream().map(d -> convertPlanMaterialParamImportDetailBO2VO(d)).collect(Collectors.toList()));
        return planMaterialParamImportResVO;
    }

    public static PlanMaterialParamImportDetailVO convertPlanMaterialParamImportDetailBO2VO(PlanMaterialParamImportDetailBO bo) {
        if (bo == null) {
            return null;
        }
        PlanMaterialParamImportDetailVO planMaterialParamImportDetailVO = new PlanMaterialParamImportDetailVO();
        planMaterialParamImportDetailVO.setIndex(bo.getIndex());
        planMaterialParamImportDetailVO.setMaterialCode(bo.getMaterialCode());
        planMaterialParamImportDetailVO.setFailedReason(bo.getFailedReason());
        return planMaterialParamImportDetailVO;
    }

    public static PlanMaterialParameterBO convertPlanMaterialParamUpdateDTO2BO(PlanMaterialParamUpdateReqDTO reqDTO) {
        if (reqDTO == null) {
            return null;
        }
        PlanMaterialParameterBO planMaterialParameterBO = new PlanMaterialParameterBO();
        planMaterialParameterBO.setId(reqDTO.getId());
        planMaterialParameterBO.setPlanTypeCode(reqDTO.getPlanType());
        planMaterialParameterBO.setDemandType(reqDTO.getDemandType());
        planMaterialParameterBO.setDemandStrategyCode(reqDTO.getDemandStrategyCode());
        planMaterialParameterBO.setVendorLeadTime(reqDTO.getVendorLeadTime());
        planMaterialParameterBO.setDemandTimeFence(reqDTO.getDemandTimeFence());
        planMaterialParameterBO.setPlanningTimeFence(reqDTO.getPlanningTimeFence());
        planMaterialParameterBO.setOrderQuantity(reqDTO.getOrderQuantity());
        planMaterialParameterBO.setMinOrderQuantity(reqDTO.getMinOrderQuantity());
        planMaterialParameterBO.setOrderCycleTime(reqDTO.getOrderCycleTime());
        planMaterialParameterBO.setSafetyStockCalType(reqDTO.getSafetyStockCalType());
        planMaterialParameterBO.setSafetyStock(reqDTO.getSafetyStock());
        if (Objects.nonNull(reqDTO.getDemandStd()) && Objects.nonNull(reqDTO.getSafetyCoefficient()) && Objects.nonNull(reqDTO.getGuaranteeInterval())) {
            Map<String, Object> factors = Maps.newHashMap();
            factors.put(SAFETY_STOCK_FACTOR_DEMAND_STD, reqDTO.getDemandStd());
            factors.put(SAFETY_STOCK_FACTOR_SAFETY_COEFFICIENT, reqDTO.getSafetyCoefficient());
            factors.put(SAFETY_STOCK_FACTOR_GUARANTEE_INTERVAL, reqDTO.getGuaranteeInterval());
            planMaterialParameterBO.setSafetyStockFactors(factors);
        }
        planMaterialParameterBO.setLossRate(reqDTO.getLossRate());
        planMaterialParameterBO.setFinishedRate(reqDTO.getFinishedRate());
        planMaterialParameterBO.setTransferLogicalPlantNo(reqDTO.getTransferLogicalPlantNo());
        return planMaterialParameterBO;
    }

    public static PlanMaterialParamQueryReqBO convertPlanMaterialParamQueryReqDTO2BO(PlanMaterialParamQueryReqDTO reqDTO) {
        if (reqDTO == null) {
            return null;
        }
        PlanMaterialParamQueryReqBO planMaterialParamQueryReqBO = new PlanMaterialParamQueryReqBO();
        planMaterialParamQueryReqBO.setMaterialCode(Optional.ofNullable(reqDTO.getMaterialCode()).orElse(reqDTO.getExternalMaterialCode()));
        planMaterialParamQueryReqBO.setLogicalPlantNo(reqDTO.getLogicalPlantNo());
        return planMaterialParamQueryReqBO;
    }

    public static PlanMaterialParamQueryResVO convertPlanMaterialParamQueryResBO2VO(PlanMaterialParameterBO bo) {
        if (bo == null) {
            return null;
        }
        PlanMaterialParamQueryResVO planMaterialParamQueryResVO = new PlanMaterialParamQueryResVO();
        planMaterialParamQueryResVO.setId(bo.getId());
        planMaterialParamQueryResVO.setMaterialCode(bo.getMaterialCode());
        planMaterialParamQueryResVO.setLogicalPlantNo(bo.getLogicalPlantNo());
        planMaterialParamQueryResVO.setLogicalPlantName(bo.getLogicalPlantName());
        planMaterialParamQueryResVO.setPlanTypeCode(bo.getPlanTypeCode());
        planMaterialParamQueryResVO.setPlanTypeDesc(bo.getPlanTypeDesc());
        planMaterialParamQueryResVO.setDemandType(bo.getDemandType());
        planMaterialParamQueryResVO.setDemandStrategyCode(bo.getDemandStrategyCode());
        planMaterialParamQueryResVO.setVendorLeadTime(bo.getVendorLeadTime());
        planMaterialParamQueryResVO.setPlanningTimeFence(bo.getPlanningTimeFence());
        planMaterialParamQueryResVO.setDemandTimeFence(bo.getDemandTimeFence());
        planMaterialParamQueryResVO.setOrderQuantity(bo.getOrderQuantity());
        planMaterialParamQueryResVO.setMinOrderQuantity(bo.getMinOrderQuantity());
        planMaterialParamQueryResVO.setOrderCycleTime(bo.getOrderCycleTime());
        planMaterialParamQueryResVO.setSafetyStockCalType(bo.getSafetyStockCalType());
        planMaterialParamQueryResVO.setSafetyStock(bo.getSafetyStock());
        if (MapUtils.isNotEmpty(bo.getSafetyStockFactors())) {
            planMaterialParamQueryResVO.setDemandStd(MapUtils.getInteger(bo.getSafetyStockFactors(),SAFETY_STOCK_FACTOR_DEMAND_STD));
            planMaterialParamQueryResVO.setSafetyCoefficient(MapUtils.getInteger(bo.getSafetyStockFactors(),SAFETY_STOCK_FACTOR_SAFETY_COEFFICIENT));
            planMaterialParamQueryResVO.setGuaranteeInterval(MapUtils.getInteger(bo.getSafetyStockFactors(),SAFETY_STOCK_FACTOR_GUARANTEE_INTERVAL));
        }
        planMaterialParamQueryResVO.setLossRate(bo.getLossRate());
        planMaterialParamQueryResVO.setFinishedRate(bo.getFinishedRate());
        planMaterialParamQueryResVO.setTransferLogicalPlantNo(bo.getTransferLogicalPlantNo());
        return planMaterialParamQueryResVO;
    }

    public static PlanMaterialParamPageReqBO convertPlanMaterialParamPageReqDTO2BO(PlanMaterialParamPageReqDTO reqDTO) {
        if (reqDTO == null) {
            return null;
        }
        PlanMaterialParamPageReqBO planMaterialParamPageReqBO = new PlanMaterialParamPageReqBO();
        planMaterialParamPageReqBO.setMaterialCode(reqDTO.getMaterialCode());
        planMaterialParamPageReqBO.setLogicalPlantNo(reqDTO.getLogicalPlantNo());
        planMaterialParamPageReqBO.setOperatorName(reqDTO.getOperatorName());
        if (reqDTO.getPageNum() != null) {
            planMaterialParamPageReqBO.setPageNum(reqDTO.getPageNum());
        }
        if (reqDTO.getPageSize() != null) {
            planMaterialParamPageReqBO.setSize(reqDTO.getPageSize());
        }
        return planMaterialParamPageReqBO;
    }

    public static PlanMaterialParamPageResVO convertPlanMaterialParamPageResBO2VO(PlanMaterialParameterBO bo) {
        if (bo == null) {
            return null;
        }
        PlanMaterialParamPageResVO planMaterialParamPageResVO = new PlanMaterialParamPageResVO();
        planMaterialParamPageResVO.setMaterialCode(bo.getMaterialCode());
        planMaterialParamPageResVO.setLogicalPlantName(bo.getLogicalPlantName());
        planMaterialParamPageResVO.setLogicalPlantNo(bo.getLogicalPlantNo());
        if (bo.getCreateTime() != null) {
            planMaterialParamPageResVO.setCreateTime(bo.getCreateTimeStr());
        }
        planMaterialParamPageResVO.setMaterialDesc(bo.getMaterialDesc());
        planMaterialParamPageResVO.setTransferLogicalPlantNo(bo.getTransferLogicalPlantNo());
        return planMaterialParamPageResVO;
    }

    public static PlanBO convertPlanCreateReqDTO2BO(PlanCreateReqDTO reqDTO) {
        if (reqDTO == null) {
            return null;
        }
        PlanBO planBO = new PlanBO();
        planBO.setPlanDesc(reqDTO.getPlanDesc());
        planBO.setCoverLogicalPlantNos(reqDTO.getCoverLogicalPlantNos());
        planBO.setCoverMaterialType(reqDTO.getCoverMaterialType());
        planBO.setPlanStartTimeStr(reqDTO.getPlanStartTimeStr());
        planBO.setPlanType(reqDTO.getPlanType());
        planBO.setPlanHorizon(reqDTO.getPlanHorizon());
        planBO.setDemandPlanIds(reqDTO.getDemandPlanIds());
        planBO.setPlanCalculateParams(reqDTO.getPlanCalculateParams());
        planBO.setPlanDeliveryParams(reqDTO.getPlanDeliveryParams());
        planBO.setRelatedBom(Optional.ofNullable(reqDTO.getRelatedBom()).orElse(0));
        return planBO;
    }

    public static PlanMaterialImportReqBO convertPlanMaterialImportReqDTO2BO(PlanMaterialImportReqDTO reqDTO, String token) {
        if (reqDTO == null) {
            return null;
        }
        PlanMaterialImportReqBO planMaterialImportReqBO = new PlanMaterialImportReqBO();
        planMaterialImportReqBO.setFile(reqDTO.getFile());
        planMaterialImportReqBO.setPlanId(reqDTO.getPlanId());
        return planMaterialImportReqBO;
    }

    public static PlanMaterialImportResVO convertPlanMaterialImportResBO2VO(PlanMaterialImportResBO resultBO) {
        if (resultBO == null) {
            return null;
        }
        PlanMaterialImportResVO planMaterialImportResVO = new PlanMaterialImportResVO();
        planMaterialImportResVO.setTotalCount(resultBO.getTotalCount());
        planMaterialImportResVO.setFailedCount(resultBO.getFailedCount());
        planMaterialImportResVO.setFailedDetails(resultBO.getFailedDetails().stream().map(d -> conPlanMaterialImportDetailBO2VO(d)).collect(Collectors.toList()));
        return planMaterialImportResVO;
    }

    public static PlanMaterialImportDetailVO conPlanMaterialImportDetailBO2VO(PlanMaterialImportDetailBO bo) {
        if (bo == null) {
            return null;
        }
        PlanMaterialImportDetailVO planMaterialImportDetailVO = new PlanMaterialImportDetailVO();
        planMaterialImportDetailVO.setIndex(bo.getIndex());
        planMaterialImportDetailVO.setMaterialCode(bo.getMaterialCode());
        planMaterialImportDetailVO.setLogicalPlantNo(bo.getLogicalPlantNo());
        planMaterialImportDetailVO.setFailedReason(bo.getFailedReason());
        return planMaterialImportDetailVO;
    }

    public static PlanQueryResVO convertPlanQueryResBO2VO(PlanBO bo) {
        if (bo == null) {
            return null;
        }
        PlanQueryResVO planQueryResVO = new PlanQueryResVO();
        planQueryResVO.setId(bo.getId());
        planQueryResVO.setPlanCode(bo.getPlanCode());
        planQueryResVO.setCoverMaterialType(bo.getCoverMaterialType());
        planQueryResVO.setPlanDesc(bo.getPlanDesc());
        planQueryResVO.setPlanType(bo.getPlanType());
        planQueryResVO.setPlanHorizon(bo.getPlanHorizon());
        planQueryResVO.setPlanStartTime(bo.getPlanStartTime());
        planQueryResVO.setPlanStartTimeStr(bo.getPlanStartTimeStr());
        planQueryResVO.setStatus(bo.getStatus());
        planQueryResVO.setUpdateTime(bo.getUpdateTime());
        planQueryResVO.setUpdateTimeStr(bo.getUpdateTimeStr());
        planQueryResVO.setDemandPlanFile(bo.getDemandPlanFile());
        planQueryResVO.setCoverLogicalPlant(bo.getCoverLogicalPlant());
        planQueryResVO.setPlanDeliveryParams(bo.getPlanDeliveryParams());
        planQueryResVO.setPlanCalculateParams(bo.getPlanCalculateParams());
        planQueryResVO.setRelatedBom(bo.getRelatedBom());
        return planQueryResVO;
    }

    public static PlanBO convertPlanUpdateReqDTO2BO(PlanUpdateReqDTO reqDTO) {
        if (reqDTO == null) {
            return null;
        }
        PlanBO planBO = new PlanBO();
        planBO.setId(reqDTO.getId());
        planBO.setPlanDesc(reqDTO.getPlanDesc());
        planBO.setCoverLogicalPlantNos(reqDTO.getCoverLogicalPlantNos());
        planBO.setCoverMaterialType(reqDTO.getCoverMaterialType());
        planBO.setPlanStartTimeStr(reqDTO.getPlanStartTimeStr());
        planBO.setPlanType(reqDTO.getPlanType());
        planBO.setPlanHorizon(reqDTO.getPlanHorizon());
        planBO.setDemandPlanIds(reqDTO.getDemandPlanIds());
        planBO.setPlanCalculateParams(reqDTO.getPlanCalculateParams());
        planBO.setPlanDeliveryParams(reqDTO.getPlanDeliveryParams());
        planBO.setRelatedBom(reqDTO.getRelatedBom());
        return planBO;
    }

    public static PlanPageReqBO convertPlanPageReqDTO2BO(PlanPageReqDTO reqDTO) {
        if (reqDTO == null) {
            return null;
        }
        PlanPageReqBO planPageReqBO = new PlanPageReqBO();
        planPageReqBO.setPlanCode(reqDTO.getPlanCode());
        planPageReqBO.setPlanDesc(reqDTO.getPlanDesc());
        planPageReqBO.setPlanType(reqDTO.getPlanType());
        planPageReqBO.setOperatorName(reqDTO.getOperatorName());
        planPageReqBO.setStatus(reqDTO.getStatus());
        if (reqDTO.getPageNum() != null) {
            planPageReqBO.setPageNum(reqDTO.getPageNum());
        }
        if (reqDTO.getPageSize() != null) {
            planPageReqBO.setSize(reqDTO.getPageSize());
        }
        return planPageReqBO;
    }

    public static PlanPageResVO convertPlanPageResBO2VO(PlanBO d) {
        if (d == null) {
            return null;
        }
        PlanPageResVO planPageResVO = new PlanPageResVO();
        planPageResVO.setPlanCode(d.getPlanCode());
        planPageResVO.setPlanDesc(d.getPlanDesc());
        planPageResVO.setPlanType(d.getPlanType());
        planPageResVO.setUpdateTime(d.getUpdateTime());
        planPageResVO.setUpdateTimeStr(d.getUpdateTimeStr());
        planPageResVO.setStatus(d.getStatus());
        planPageResVO.setId(d.getId());
        planPageResVO.setCoverMaterialType(d.getCoverMaterialType());
        planPageResVO.setExported(d.getExported());
        planPageResVO.setRelatedBom(d.getRelatedBom());
        return planPageResVO;
    }

    public static ChangeStatusPlanBO convertPlanChangeStatusReqDTO2BO(PlanChangeStatusReqDTO reqDTO, UserContext userInfo) {
        if (reqDTO == null) {
            return null;
        }
        ChangeStatusPlanBO changeStatusPlanBO = new ChangeStatusPlanBO();
        changeStatusPlanBO.setId(reqDTO.getId());
        changeStatusPlanBO.setStatus(reqDTO.getStatus());
        if (userInfo != null) {
            changeStatusPlanBO.setTenantId(userInfo.getTenantId());
        }
        return changeStatusPlanBO;
    }

    public static PlanMaterialParamImportReqBO convertPlanMaterialParamImportReqDTO2BO(PlanMaterialParamImportReqDTO reqDTO, String token) {
        if (reqDTO == null) {
            return null;
        }
        PlanMaterialParamImportReqBO planMaterialParamImportReqBO = new PlanMaterialParamImportReqBO();
        planMaterialParamImportReqBO.setFile(reqDTO.getFile());
        return planMaterialParamImportReqBO;
    }

    public static PlanParamEnumResVO convertPlanParamBO2VO(PlanParamEnumResBO bo) {
        if (Objects.isNull(bo)) {
            return null;
        }
        PlanParamEnumResVO result = new PlanParamEnumResVO();
        return result;
    }

    public static PlanMaterialParamEnumResVO convertPlanMaterialParamBO2VO(PlanMaterialParamEnumResBO bo) {
        if (Objects.isNull(bo)) {
            return null;
        }
        PlanMaterialParamEnumResVO resVO = new PlanMaterialParamEnumResVO();
        return resVO;
    }

    public static QueryPlanTransferLogicalPlantsReqBO convertQueryPlanTransferLogicalPlantNoBO(String materialCode,String logicalPlantNo,String tenantId) {
        QueryPlanTransferLogicalPlantsReqBO queryPlanTransferLogicalPlantsReqBO = new QueryPlanTransferLogicalPlantsReqBO();
        queryPlanTransferLogicalPlantsReqBO.setMaterialCode(materialCode);
        queryPlanTransferLogicalPlantsReqBO.setTenantId(tenantId);
        queryPlanTransferLogicalPlantsReqBO.setLogicalPlantNo(logicalPlantNo);
        return queryPlanTransferLogicalPlantsReqBO;
    }

    public static PlanMaterialLogicalPlantResVO convertPlanTransferLogicalPlantsResBO2VO(PlanTransferLogicalPlantsResBO resBO) {
        if(resBO == null){
            return null;
        }
        PlanMaterialLogicalPlantResVO resVo = new PlanMaterialLogicalPlantResVO();
        List<LogicalPlantsResBO> transferLogicalPlants = resBO.getTransferLogicalPlants();
        if(CollectionUtils.isEmpty(transferLogicalPlants)){
            return resVo;
        }
        List<PlanMaterialLogicalPlantVO> voList=new ArrayList<>();
        transferLogicalPlants.forEach(bo->{
            PlanMaterialLogicalPlantVO vo=new PlanMaterialLogicalPlantVO();
            vo.setLogicalPlantNo(bo.getLogicalPlantNo());
            vo.setLogicalPlantName(bo.getLogicalPlantName());
            voList.add(vo);
        });
        resVo.setLogicalPlantList(voList);
        return resVo;
    }
}
