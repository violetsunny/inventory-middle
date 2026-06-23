package com.inventory.middle.application.plan.config.convertor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.inventory.middle.domain.plan.common.constants.CommonConstants;
import com.inventory.middle.domain.plan.common.enums.MaterialSceneTypeEnum;
import com.inventory.middle.infra.plan.util.DateUtils;
import com.inventory.middle.application.plan.config.bo.*;
import com.inventory.middle.domain.plan.common.enums.PlanMaterialParamPlanTypeEnum;
import com.inventory.middle.infra.plan.persistence.condition.*;
import com.inventory.middle.infra.plan.persistence.entity.ChangeStatusPlanPO;
import com.inventory.middle.infra.plan.persistence.entity.PlanMaterialPO;
import com.inventory.middle.infra.plan.persistence.entity.PlanMaterialParameterPO;
import com.inventory.middle.infra.plan.persistence.entity.PlanPO;
import com.inventory.middle.client.plan.dto.inventory.LogicalPlant;
import com.inventory.middle.client.plan.dto.inventory.InvPlantBO;
import com.inventory.middle.client.plan.dto.product.ProductBO;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Map;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 计划子欲计划配置转换器
 * @date 2021/9/28 10:02
 */
public class PlanConfigConverter {

    public static PlanMaterialParameterPO convertCreatePlanMaterialParameterBO2PO(PlanMaterialParameterBO bo) {
        if (bo == null) {
            return null;
        }

        PlanMaterialParameterPO planMaterialParameterPO = new PlanMaterialParameterPO();
        String materialCode = bo.getMaterialCode();
        planMaterialParameterPO.setMaterialCode(materialCode);
        // 物料名称
        planMaterialParameterPO.setMaterialDesc(bo.getMaterialDesc());
        // 逻辑仓名称
        planMaterialParameterPO.setLogicalPlantName(bo.getLogicalPlantName());
        planMaterialParameterPO.setPlanType(bo.getPlanTypeCode());
        planMaterialParameterPO.setDemandType(bo.getDemandType());
        planMaterialParameterPO.setDemandStrategyCode(bo.getDemandStrategyCode());
        planMaterialParameterPO.setVendorLeadTime(bo.getVendorLeadTime());
        planMaterialParameterPO.setDemandTimeFence(bo.getDemandTimeFence());
        planMaterialParameterPO.setPlanningTimeFence(bo.getPlanningTimeFence());
        planMaterialParameterPO.setOrderQuantity(bo.getOrderQuantity());
        planMaterialParameterPO.setMinOrderQuantity(bo.getMinOrderQuantity());
        planMaterialParameterPO.setOrderCycleTime(bo.getOrderCycleTime());
        planMaterialParameterPO.setSafetyStockCalType(bo.getSafetyStockCalType());
        planMaterialParameterPO.setSafetyStock(bo.getSafetyStock());
        if (MapUtils.isNotEmpty(bo.getSafetyStockFactors())) {
            planMaterialParameterPO.setSafetyStockFactors(JSON.toJSONString(bo.getSafetyStockFactors()));
        }
        planMaterialParameterPO.setLossRate(bo.getLossRate());
        planMaterialParameterPO.setFinishedRate(bo.getFinishedRate());
        planMaterialParameterPO.setLogicalPlantNo(bo.getLogicalPlantNo());
        planMaterialParameterPO.setCreatorId(bo.getUserId());
        planMaterialParameterPO.setUpdatorId(bo.getUserId());
        planMaterialParameterPO.setOperatorName(bo.getUserName());
        planMaterialParameterPO.setTenantId(bo.getTenantId());
        planMaterialParameterPO.setTransferLogicalPlantNo(bo.getTransferLogicalPlantNo());
        return planMaterialParameterPO;
    }

    public static PlanMaterialParameterPO convertUpdatePlanMaterialParameterBO2PO(PlanMaterialParameterBO bo) {
        if (bo == null) {
            return null;
        }
        PlanMaterialParameterPO planMaterialParameterPO = new PlanMaterialParameterPO();
        planMaterialParameterPO.setId(bo.getId());
        planMaterialParameterPO.setMaterialCode(bo.getMaterialCode());
        planMaterialParameterPO.setLogicalPlantNo(bo.getLogicalPlantNo());
        planMaterialParameterPO.setPlanType(bo.getPlanTypeCode());
        planMaterialParameterPO.setDemandType(bo.getDemandType());
        planMaterialParameterPO.setDemandStrategyCode(bo.getDemandStrategyCode());
        planMaterialParameterPO.setVendorLeadTime(bo.getVendorLeadTime());
        planMaterialParameterPO.setDemandTimeFence(bo.getDemandTimeFence());
        planMaterialParameterPO.setPlanningTimeFence(bo.getPlanningTimeFence());
        planMaterialParameterPO.setOrderQuantity(bo.getOrderQuantity());
        planMaterialParameterPO.setMinOrderQuantity(bo.getMinOrderQuantity());
        planMaterialParameterPO.setOrderCycleTime(bo.getOrderCycleTime());
        planMaterialParameterPO.setSafetyStockCalType(bo.getSafetyStockCalType());
        planMaterialParameterPO.setSafetyStock(bo.getSafetyStock());
        if(MapUtils.isNotEmpty(bo.getSafetyStockFactors())){
            planMaterialParameterPO.setSafetyStockFactors(JSON.toJSONString(bo.getSafetyStockFactors()));
        }
        planMaterialParameterPO.setLossRate(bo.getLossRate());
        planMaterialParameterPO.setFinishedRate(bo.getFinishedRate());
        planMaterialParameterPO.setUpdatorId(bo.getUserId());
        planMaterialParameterPO.setOperatorName(bo.getUserName());
        planMaterialParameterPO.setTenantId(bo.getTenantId());
        planMaterialParameterPO.setTransferLogicalPlantNo(bo.getTransferLogicalPlantNo());
        return planMaterialParameterPO;
    }

    public static PlanMaterialParamQueryCondition convertPlanMaterialParamQueryReqBO2Condition(PlanMaterialParamQueryReqBO bo) {
        if (bo == null) {
            return null;
        }
        PlanMaterialParamQueryCondition planMaterialParamQueryCondition = new PlanMaterialParamQueryCondition();
        planMaterialParamQueryCondition.setTenantId(bo.getTenantId());
        planMaterialParamQueryCondition.setMaterialCode(bo.getMaterialCode());
        planMaterialParamQueryCondition.setLogicalPlantNo(bo.getLogicalPlantNo());
        return planMaterialParamQueryCondition;
    }

    public static PlanMaterialParameterBO convertPlanMaterialParameterPO2BO(PlanMaterialParameterPO po) {
        if (po == null) {
            return null;
        }
        PlanMaterialParameterBO planMaterialParameterBO = new PlanMaterialParameterBO();
        planMaterialParameterBO.setId(po.getId());
        planMaterialParameterBO.setMaterialCode(po.getMaterialCode());
        planMaterialParameterBO.setLogicalPlantNo(po.getLogicalPlantNo());
        planMaterialParameterBO.setLogicalPlantName(po.getLogicalPlantName());
        planMaterialParameterBO.setPlanTypeCode(po.getPlanType());
        planMaterialParameterBO.setPlanTypeDesc(PlanMaterialParamPlanTypeEnum.getByCode(po.getPlanType()).getDesc());
        planMaterialParameterBO.setDemandType(po.getDemandType());
        planMaterialParameterBO.setDemandStrategyCode(po.getDemandStrategyCode());
        planMaterialParameterBO.setVendorLeadTime(po.getVendorLeadTime());
        planMaterialParameterBO.setPlanningTimeFence(po.getPlanningTimeFence());
        planMaterialParameterBO.setDemandTimeFence(po.getDemandTimeFence());
        planMaterialParameterBO.setOrderQuantity(po.getOrderQuantity());
        planMaterialParameterBO.setMinOrderQuantity(po.getMinOrderQuantity());
        planMaterialParameterBO.setOrderCycleTime(po.getOrderCycleTime());
        planMaterialParameterBO.setSafetyStockCalType(po.getSafetyStockCalType());
        planMaterialParameterBO.setSafetyStock(po.getSafetyStock());
        if (StringUtils.isNotEmpty(po.getSafetyStockFactors())) {
            planMaterialParameterBO.setSafetyStockFactors(JSON.parseObject(po.getSafetyStockFactors(),
                    new TypeReference<Map<String, Object>>() {
                    }));
        }
        planMaterialParameterBO.setLossRate(po.getLossRate());
        planMaterialParameterBO.setFinishedRate(po.getFinishedRate());
        planMaterialParameterBO.setCreateTime(po.getCreateTime());
        planMaterialParameterBO.setCreateTimeStr(DateUtils.dateToString(po.getCreateTime(), DateUtils.yyyy_MM_dd_HH_mm_ss));
        planMaterialParameterBO.setUserId(po.getUpdatorId());
        planMaterialParameterBO.setUserName(po.getOperatorName());
        planMaterialParameterBO.setTenantId(po.getTenantId());
        planMaterialParameterBO.setMaterialDesc(po.getMaterialDesc());
        planMaterialParameterBO.setTransferLogicalPlantNo(po.getTransferLogicalPlantNo());
        return planMaterialParameterBO;
    }

    public static PlanMaterialParamPageCondition convertPlanMaterialParamPageReqBO2Condition(PlanMaterialParamPageReqBO bo) {
        if (bo == null) {
            return null;
        }
        PlanMaterialParamPageCondition planMaterialParamPageCondition = new PlanMaterialParamPageCondition();
        planMaterialParamPageCondition.setTenantId(bo.getTenantId());
        planMaterialParamPageCondition.setMaterialCode(bo.getMaterialCode());
        planMaterialParamPageCondition.setLogicalPlantNo(bo.getLogicalPlantNo());
        planMaterialParamPageCondition.setOperatorName(bo.getOperatorName());
        planMaterialParamPageCondition.setPageNum(bo.getPageNum());
        planMaterialParamPageCondition.setSize(bo.getSize());
        return planMaterialParamPageCondition;
    }

    public static PlanPO convertUpdatePlanBO2PO(PlanBO bo) {
        if (bo == null) {
            return null;
        }
        PlanPO planPO = new PlanPO();
        planPO.setId(bo.getId());
        planPO.setPlanDesc(bo.getPlanDesc());
        planPO.setPlanType(bo.getPlanType());
        planPO.setPlanHorizon(bo.getPlanHorizon());
        planPO.setPlanStartTime(bo.getPlanStartTime());
        planPO.setTenantId(bo.getTenantId());
        planPO.setCoverMaterialType(bo.getCoverMaterialType());
        planPO.setCoverLogicalPlant(String.join(CommonConstants.DELIMITER_COMMA, bo.getCoverLogicalPlantNos()));
//        planPO.setDemandPlanFile(String.join(CommonConstants.DELIMITER_COMMA, bo.getDemandPlanIds()));
        planPO.setPlanCalculateParams(JSON.toJSONString(bo.getPlanCalculateParams()));
        planPO.setPlanDeliveryParams(JSON.toJSONString(bo.getPlanDeliveryParams()));
        planPO.setUpdatorId(bo.getUserId());
        planPO.setOperatorName(bo.getUserName());
        planPO.setRelatedBom(bo.getRelatedBom());
        return planPO;
    }

    public static PlanBO convertPlanPO2BO(PlanPO po) {
        if (po == null) {
            return null;
        }
        PlanBO planBO = new PlanBO();
        planBO.setId(po.getId());
        planBO.setPlanCode(po.getPlanCode());
        planBO.setPlanDesc(po.getPlanDesc());
        planBO.setPlanType(po.getPlanType());
        planBO.setCoverMaterialType(po.getCoverMaterialType());
        planBO.setPlanHorizon(po.getPlanHorizon());
        planBO.setPlanStartTime(po.getPlanStartTime());
        planBO.setPlanStartTimeStr(DateUtils.dateToString(po.getPlanStartTime(), DateUtils.yyyy_MM_dd));
        planBO.setUpdateTime(po.getUpdateTime());
        planBO.setUpdateTimeStr(DateUtils.dateToString(po.getUpdateTime(), DateUtils.yyyy_MM_dd_HH_mm_ss));
        planBO.setStatus(po.getStatus());
        if (StringUtils.isNotEmpty(po.getDemandPlanFile())) {
            planBO.setDemandPlanIds(Arrays.asList(po.getDemandPlanFile().split(CommonConstants.DELIMITER_COMMA)));
        }
        planBO.setCoverLogicalPlantNos(Arrays.asList(po.getCoverLogicalPlant().split(CommonConstants.DELIMITER_COMMA)));
        planBO.setUserId(po.getUpdatorId());
        planBO.setCreateUserId(po.getCreatorId());
        planBO.setUpdateUserId(po.getUpdatorId());
        planBO.setUserName(po.getOperatorName());
        planBO.setTenantId(po.getTenantId());
        planBO.setExported(0);
        planBO.setRelatedBom(po.getRelatedBom());
        planBO.setPlanCalculateParams(JSON.parseObject(po.getPlanCalculateParams(), new TypeReference<Map<String, Object>>() {
        }));
        planBO.setPlanDeliveryParams(JSON.parseObject(po.getPlanDeliveryParams(), new TypeReference<Map<String, Object>>() {
        }));
        return planBO;
    }

    public static PlanPageCondition convertPlanPageReqBO2Condition(PlanPageReqBO bo) {
        if (bo == null) {
            return null;
        }
        PlanPageCondition condition = new PlanPageCondition();
        condition.setPlanCode(bo.getPlanCode());
        condition.setPlanDesc(bo.getPlanDesc());
        condition.setPlanType(bo.getPlanType());
        condition.setOperatorName(bo.getOperatorName());
        condition.setTenantId(bo.getTenantId());
        condition.setStatus(bo.getStatus());
        condition.setPageNum(bo.getPageNum());
        condition.setSize(bo.getSize());
        return condition;
    }

    public static PlanMaterialPO convertPlanMaterialBO2PO(PlanMaterialBO bo) {
        if (bo == null) {
            return null;
        }
        PlanMaterialPO planMaterialPO = new PlanMaterialPO();
        planMaterialPO.setId(bo.getId());
        planMaterialPO.setMaterialCode(bo.getMaterialCode());
        planMaterialPO.setLogicalPlantNo(bo.getLogicalPlantNo());
        planMaterialPO.setSourceId(bo.getSourceId());
        planMaterialPO.setCreateTime(bo.getCreateTime());
        planMaterialPO.setUpdateTime(bo.getUpdateTime());
        planMaterialPO.setCreatorId(bo.getOperatorId());
        planMaterialPO.setUpdatorId(bo.getOperatorId());
        planMaterialPO.setTenantId(bo.getTenantId());
        planMaterialPO.setType(MaterialSceneTypeEnum.PLAN_MATERIAL_TYPE.getCode());
        return planMaterialPO;
    }

    public static PlanMaterialQueryCondition convertPlanMaterialBO2QueryCondition(PlanMaterialBO bo) {
        if (bo == null) {
            return null;
        }
        PlanMaterialQueryCondition planMaterialQueryCondition = new PlanMaterialQueryCondition();
        planMaterialQueryCondition.setMaterialCode(bo.getMaterialCode());
        planMaterialQueryCondition.setLogicalPlantNo(bo.getLogicalPlantNo());
        return planMaterialQueryCondition;
    }

    public static PlanMaterialBatchCreateDetailBO convertPlanMaterialBO2CreateDetail(PlanMaterialBO bo, String createMessage) {
        if (bo == null) {
            return null;
        }
        PlanMaterialBatchCreateDetailBO planMaterialBatchCreateDetailBO = new PlanMaterialBatchCreateDetailBO();
        planMaterialBatchCreateDetailBO.setId(bo.getId());
        planMaterialBatchCreateDetailBO.setMaterialCode(bo.getMaterialCode());
        planMaterialBatchCreateDetailBO.setLogicalPlantNo(bo.getLogicalPlantNo());
        planMaterialBatchCreateDetailBO.setCreateMessage(createMessage);
        planMaterialBatchCreateDetailBO.setIndex(bo.getIndex());
        return planMaterialBatchCreateDetailBO;
    }

    public static PlanQueryByTypeCondition convertPlanQueryByTypeReqBO2Condition(PlanQueryByTypeReqBO bo) {
        if (bo == null) {
            return null;
        }
        PlanQueryByTypeCondition planQueryByTypeCondition = new PlanQueryByTypeCondition();
        planQueryByTypeCondition.setTenantId(bo.getTenantId());
        planQueryByTypeCondition.setPlanType(bo.getPlanType());
        return planQueryByTypeCondition;
    }

    public static PlanMaterialBO convertPlanMaterialPO2BO(PlanMaterialPO po) {
        if (po == null) {
            return null;
        }
        PlanMaterialBO planMaterialBO = new PlanMaterialBO();
        planMaterialBO.setId(po.getId());
        planMaterialBO.setSourceId(po.getSourceId());
        planMaterialBO.setMaterialCode(po.getMaterialCode());
        planMaterialBO.setLogicalPlantNo(po.getLogicalPlantNo());
        planMaterialBO.setCreateTime(po.getCreateTime());
        planMaterialBO.setUpdateTime(po.getUpdateTime());
        planMaterialBO.setTenantId(po.getTenantId());
        return planMaterialBO;
    }

    public static PlanPO convertCreatePlanBO2PO(PlanBO bo, String planCode) {

        if (bo == null) {
            return null;
        }
        PlanPO planPO = new PlanPO();
        planPO.setPlanCode(planCode);
        planPO.setPlanDesc(bo.getPlanDesc());
        planPO.setPlanType(bo.getPlanType());
        planPO.setPlanHorizon(bo.getPlanHorizon());
        planPO.setPlanStartTime(bo.getPlanStartTime());
        planPO.setTenantId(bo.getTenantId());
        planPO.setCoverMaterialType(bo.getCoverMaterialType());
        planPO.setCoverLogicalPlant(String.join(CommonConstants.DELIMITER_COMMA, bo.getCoverLogicalPlantNos()));
//        planPO.setDemandPlanFile(String.join(CommonConstants.DELIMITER_COMMA, bo.getDemandPlanIds()));
        planPO.setPlanCalculateParams(JSON.toJSONString(bo.getPlanCalculateParams()));
        planPO.setPlanDeliveryParams(JSON.toJSONString(bo.getPlanDeliveryParams()));
        planPO.setCreatorId(bo.getUserId());
        planPO.setUpdatorId(bo.getUserId());
        planPO.setOperatorName(bo.getUserName());
        planPO.setRelatedBom(bo.getRelatedBom());
        return planPO;
    }

    public static ChangeStatusPlanPO convertChangePlanStatusBO2PO(ChangeStatusPlanBO bo) {
        if (bo == null) {
            return null;
        }
        ChangeStatusPlanPO po = new ChangeStatusPlanPO();
        po.setStatus(bo.getStatus());
        po.setId(bo.getId());
        return po;
    }

    /**
     * @param planMaterialParameterBO
     * @param logicalPlantMap
     * @return
     */
    public static PlanMaterialParameterBO convertPlanMaterialParameterBO2BO(PlanMaterialParameterBO planMaterialParameterBO, Map<String, InvPlantBO> logicalPlantMap, Map<String, ProductBO> skuResponseMap) {

        if (null == planMaterialParameterBO) {
            return null;
        }
        PlanMaterialParameterBO boTemp = new PlanMaterialParameterBO();
        // 设置物料名称
        if (MapUtils.isNotEmpty(skuResponseMap) && skuResponseMap.containsKey(planMaterialParameterBO.getMaterialCode())) {
            boTemp.setMaterialDesc(skuResponseMap.get(planMaterialParameterBO.getMaterialCode()).getName());
        } else {
            boTemp.setMaterialDesc(planMaterialParameterBO.getMaterialDesc());
        }
        boTemp.setTransferLogicalPlantNo(planMaterialParameterBO.getTransferLogicalPlantNo());
        boTemp.setPlanTypeCode(planMaterialParameterBO.getPlanTypeCode());
        boTemp.setUserId(planMaterialParameterBO.getUserId());
        boTemp.setUserName(planMaterialParameterBO.getUserName());
        boTemp.setPlanningTimeFence(planMaterialParameterBO.getPlanningTimeFence());
        boTemp.setId(planMaterialParameterBO.getId());
        boTemp.setTenantId(planMaterialParameterBO.getTenantId());
        boTemp.setDemandTimeFence(planMaterialParameterBO.getDemandTimeFence());
        boTemp.setMaterialCode(planMaterialParameterBO.getMaterialCode());
        boTemp.setCreateTime(planMaterialParameterBO.getCreateTime());
        boTemp.setIndex(planMaterialParameterBO.getIndex());
        boTemp.setCreateTimeStr(planMaterialParameterBO.getCreateTimeStr());
        boTemp.setLogicalPlantNo(planMaterialParameterBO.getLogicalPlantNo());
        boTemp.setVendorLeadTime(planMaterialParameterBO.getVendorLeadTime());
        boTemp.setOrderQuantity(planMaterialParameterBO.getOrderQuantity());
        boTemp.setMinOrderQuantity(planMaterialParameterBO.getMinOrderQuantity());
        boTemp.setOrderCycleTime(planMaterialParameterBO.getOrderCycleTime());
        boTemp.setSafetyStockCalType(planMaterialParameterBO.getSafetyStockCalType());
        boTemp.setSafetyStock(planMaterialParameterBO.getSafetyStock());
        boTemp.setSafetyStockFactors(planMaterialParameterBO.getSafetyStockFactors());
        boTemp.setLossRate(planMaterialParameterBO.getLossRate());
        boTemp.setFinishedRate(planMaterialParameterBO.getFinishedRate());
        boTemp.setToken(planMaterialParameterBO.getToken());
        boTemp.setPlanTypeDesc(planMaterialParameterBO.getPlanTypeDesc());
        // 逻辑仓名称
        if (MapUtils.isNotEmpty(logicalPlantMap) && logicalPlantMap.containsKey(planMaterialParameterBO.getLogicalPlantNo())) {
            boTemp.setLogicalPlantName(logicalPlantMap.get(planMaterialParameterBO.getLogicalPlantNo()).getPlantName());
        } else {
            boTemp.setLogicalPlantName(planMaterialParameterBO.getLogicalPlantName());
        }
        boTemp.setDemandType(planMaterialParameterBO.getDemandType());
        boTemp.setDemandStrategyCode(planMaterialParameterBO.getDemandStrategyCode());
        return boTemp;
    }

    public static PlanMaterialBatchDeleteDetailBO convertPlanMaterialBO2DeleteDetail(PlanMaterialBO bo, String desc) {
        if (bo == null) {
            return null;
        }
        PlanMaterialBatchDeleteDetailBO planMaterialBatchDeleteDetailBO = new PlanMaterialBatchDeleteDetailBO();
        planMaterialBatchDeleteDetailBO.setId(bo.getId());
        planMaterialBatchDeleteDetailBO.setMaterialCode(bo.getMaterialCode());
        planMaterialBatchDeleteDetailBO.setLogicalPlantNo(bo.getLogicalPlantNo());
        planMaterialBatchDeleteDetailBO.setDeleteMessage(desc);
        planMaterialBatchDeleteDetailBO.setIndex(bo.getIndex());
        return planMaterialBatchDeleteDetailBO;
    }

    public static PlanQueryPlanTransferLogicalPlantsCondition convertPlanQueryPlanTransferLogicalPlantsBO2Condition(QueryPlanTransferLogicalPlantsReqBO reqBO) {
        if (reqBO == null) {
            return null;
        }
        PlanQueryPlanTransferLogicalPlantsCondition planQueryPlanTransferLogicalPlantsCondition = new PlanQueryPlanTransferLogicalPlantsCondition();
        planQueryPlanTransferLogicalPlantsCondition.setMaterialCode(reqBO.getMaterialCode());
        planQueryPlanTransferLogicalPlantsCondition.setTenantId(reqBO.getTenantId());
        planQueryPlanTransferLogicalPlantsCondition.setLogicalPlantNo(reqBO.getLogicalPlantNo());
        return planQueryPlanTransferLogicalPlantsCondition;
    }
}