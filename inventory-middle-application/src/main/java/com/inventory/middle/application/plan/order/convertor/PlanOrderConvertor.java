package com.inventory.middle.application.plan.order.convertor;

import com.inventory.middle.client.plan.config.dto.ManualPlanOrderCreateDTO;
import com.inventory.middle.client.plan.config.dto.PlanOrderDTO;
import com.inventory.middle.client.plan.config.dto.PlanOrderIssueDetailDTO;
import com.inventory.middle.client.plan.config.dto.PlanOrderIssueDetailPageReqDTO;
import com.inventory.middle.client.plan.config.dto.PlanOrderIssueReqDTO;
import com.inventory.middle.client.plan.config.dto.PlanOrderPageReqDTO;
import com.inventory.middle.domain.plan.common.enums.PlanMaterialParamPlanTypeEnum;
import com.inventory.middle.domain.plan.common.enums.PlanOrderCreateTypeEnum;
import com.inventory.middle.domain.plan.common.enums.PlanOrderStatusEnum;
import com.inventory.middle.infra.plan.persistence.condition.PlanOrderIssueDetailPageCondition;
import com.inventory.middle.infra.plan.persistence.condition.PlanOrderPageCondition;
import com.inventory.middle.infra.plan.persistence.entity.PlanOrderIssueDetailPO;
import com.inventory.middle.infra.plan.persistence.entity.PlanOrderPO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 计划订单转换器。
 */
public final class PlanOrderConvertor {

    private PlanOrderConvertor() {
    }

    public static PlanOrderPO toManualCreatePO(ManualPlanOrderCreateDTO dto, String orderNo, Integer planType) {
        if (dto == null) {
            return null;
        }
        Date now = new Date();
        PlanOrderPO po = new PlanOrderPO();
        po.setOrderNo(orderNo);
        po.setPlanType(planType);
        po.setCreateType(PlanOrderCreateTypeEnum.MANUAL.getCode());
        po.setMaterialCode(dto.getMaterialCode());
        po.setMaterialDesc(dto.getMaterialDesc());
        po.setLogicalPlantNo(dto.getLogicalPlantNo());
        po.setLogicalPlantName(dto.getLogicalPlantName());
        po.setPlanOrderQuantity(dto.getPlanOrderQuantity());
        po.setUnit(dto.getUnit());
        po.setForecastInventory(dto.getForecastInventory());
        po.setPlanIssueTime(dto.getPlanIssueTime());
        po.setPlanReceivingTime(dto.getPlanReceivingTime());
        po.setIssueQuantity(0);
        po.setConfirmQuantity(0);
        po.setFinishQuantity(0);
        po.setPlannerId(dto.getUserId());
        po.setPlannerName(dto.getUserName());
        po.setOperatorName(dto.getUserName());
        po.setTenantId(dto.getTenantId());
        po.setCreatorId(dto.getUserId());
        po.setUpdatorId(dto.getUserId());
        po.setCreateTime(now);
        po.setUpdateTime(now);
        po.setDeleted(0);
        po.setStatus(resolveInitialStatus(dto.getPlanReceivingTime()));
        return po;
    }

    public static PlanOrderDTO toDTO(PlanOrderPO po) {
        if (po == null) {
            return null;
        }
        PlanOrderDTO dto = new PlanOrderDTO();
        dto.setId(po.getId());
        dto.setPlanCode(po.getPlanCode());
        dto.setOrderNo(po.getOrderNo());
        dto.setMaterialCode(po.getMaterialCode());
        dto.setMaterialDesc(po.getMaterialDesc());
        dto.setExternalMaterialCode(po.getExternalMaterialCode());
        dto.setPlanType(po.getPlanType());
        dto.setCreateType(po.getCreateType());
        dto.setLogicalPlantNo(po.getLogicalPlantNo());
        dto.setLogicalPlantName(po.getLogicalPlantName());
        dto.setForecastInventory(po.getForecastInventory());
        dto.setPlanOrderQuantity(po.getPlanOrderQuantity());
        dto.setUnit(po.getUnit());
        dto.setIssueQuantity(po.getIssueQuantity());
        dto.setConfirmQuantity(po.getConfirmQuantity());
        dto.setFinishQuantity(po.getFinishQuantity());
        dto.setStatus(po.getStatus());
        dto.setPlanIssueTime(po.getPlanIssueTime());
        dto.setRealIssueTime(po.getRealIssueTime());
        dto.setPlanReceivingTime(po.getPlanReceivingTime());
        dto.setRealReceivingTime(po.getRealReceivingTime());
        dto.setConfirmTime(po.getConfirmTime());
        dto.setPlannerName(po.getPlannerName());
        dto.setPlannerId(po.getPlannerId());
        dto.setDeleted(po.getDeleted());
        dto.setCreateTime(po.getCreateTime());
        dto.setCreatorId(po.getCreatorId());
        dto.setUpdateTime(po.getUpdateTime());
        dto.setUpdatorId(po.getUpdatorId());
        dto.setOperatorName(po.getOperatorName());
        dto.setTenantId(po.getTenantId());
        dto.setDemandInfo(po.getDemandInfo());
        return dto;
    }

    public static void mergeForUpdate(PlanOrderDTO dto, PlanOrderPO target) {
        target.setMaterialCode(dto.getMaterialCode());
        target.setMaterialDesc(dto.getMaterialDesc());
        target.setLogicalPlantNo(dto.getLogicalPlantNo());
        target.setLogicalPlantName(dto.getLogicalPlantName());
        target.setPlanOrderQuantity(dto.getPlanOrderQuantity());
        target.setUnit(dto.getUnit());
        target.setForecastInventory(dto.getForecastInventory());
        target.setPlanIssueTime(dto.getPlanIssueTime());
        target.setPlanReceivingTime(dto.getPlanReceivingTime());
        target.setUpdatorId(dto.getUserId());
        target.setOperatorName(dto.getUserName());
        target.setUpdateTime(new Date());
        target.setStatus(resolveInitialStatus(dto.getPlanReceivingTime(), target.getStatus()));
    }

    public static PlanOrderPageCondition toPageCondition(PlanOrderPageReqDTO dto) {
        PlanOrderPageCondition condition = new PlanOrderPageCondition();
        condition.setOrderNo(dto.getOrderNo());
        condition.setMaxCreateTime(dto.getMaxCreateTime());
        condition.setMinCreateTime(dto.getMinCreateTime());
        condition.setMaterialCode(dto.getMaterialCode());
        condition.setLogicalPlantNo(dto.getLogicalPlantNo());
        condition.setTenantId(dto.getTenantId());
        condition.setPageNum(dto.getPageNum());
        condition.setSize(dto.getPageSize());
        List<Integer> statusList = new ArrayList<>();
        if (dto.getStatus() != null) {
            statusList.add(dto.getStatus());
        } else {
            for (PlanOrderStatusEnum statusEnum : PlanOrderStatusEnum.values()) {
                statusList.add(statusEnum.getCode());
            }
        }
        condition.setStatusList(statusList);
        return condition;
    }

    public static PlanOrderIssueDetailPageCondition toIssueDetailPageCondition(PlanOrderIssueDetailPageReqDTO dto) {
        PlanOrderIssueDetailPageCondition condition = new PlanOrderIssueDetailPageCondition();
        condition.setPlanOrderId(dto.getPlanOrderId());
        condition.setTenantId(dto.getTenantId());
        condition.setPageNum(dto.getPageNum());
        condition.setSize(dto.getPageSize());
        return condition;
    }

    public static PlanOrderIssueDetailPO toIssueDetailPO(PlanOrderPO planOrderPO, PlanOrderIssueReqDTO dto, String issueNo) {
        Date now = new Date();
        PlanOrderIssueDetailPO po = new PlanOrderIssueDetailPO();
        po.setIssueNo(issueNo);
        po.setPlanOrderId(planOrderPO.getId());
        po.setMaterialCode(planOrderPO.getMaterialCode());
        po.setLogicalPlantNo(planOrderPO.getLogicalPlantNo());
        po.setIssueQuantity(dto.getIssueQuantity());
        po.setFinishQuantity(0);
        po.setCurrentStatus("已下发");
        po.setTenantId(dto.getTenantId());
        po.setOperatorName(dto.getUserName());
        po.setCreatorId(dto.getUserId());
        po.setUpdatorId(dto.getUserId());
        po.setCreateTime(now);
        po.setUpdateTime(now);
        po.setDeleted(0);
        return po;
    }

    public static PlanOrderIssueDetailDTO toDTO(PlanOrderIssueDetailPO po) {
        if (po == null) {
            return null;
        }
        PlanOrderIssueDetailDTO dto = new PlanOrderIssueDetailDTO();
        dto.setIssueNo(po.getIssueNo());
        dto.setPlanOrderId(po.getPlanOrderId());
        dto.setIssueQuantity(po.getIssueQuantity());
        dto.setFinishQuantity(po.getFinishQuantity());
        dto.setCurrentStatus(po.getCurrentStatus());
        dto.setCreateTime(po.getCreateTime());
        dto.setUpdateTime(po.getUpdateTime());
        dto.setOperatorName(po.getOperatorName());
        return dto;
    }

    public static Integer defaultPlanType() {
        return PlanMaterialParamPlanTypeEnum.PURCHASE.getCode();
    }

    private static Integer resolveInitialStatus(Date planReceivingTime) {
        return resolveInitialStatus(planReceivingTime, PlanOrderStatusEnum.CREATED.getCode());
    }

    private static Integer resolveInitialStatus(Date planReceivingTime, Integer fallbackStatus) {
        if (planReceivingTime != null && planReceivingTime.before(new Date())) {
            return PlanOrderStatusEnum.OVERDUE.getCode();
        }
        return fallbackStatus;
    }
}
