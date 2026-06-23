package com.inventory.middle.interfaces.web.plan;

import com.inventory.middle.client.plan.plan.dto.ManualPlanOrderCreateDTO;
import com.inventory.middle.client.plan.plan.dto.PlanOrderDTO;
import com.inventory.middle.client.plan.plan.dto.PlanOrderIssueDetailDTO;
import com.inventory.middle.client.plan.plan.dto.PlanOrderIssueDetailPageReqDTO;
import com.inventory.middle.client.plan.plan.dto.PlanOrderIssueReqDTO;
import com.inventory.middle.client.plan.plan.dto.PlanOrderPageReqDTO;
import com.inventory.middle.domain.plan.common.enums.PlanMaterialParamPlanTypeEnum;
import com.inventory.middle.domain.plan.common.enums.PlanOrderCreateTypeEnum;
import com.inventory.middle.domain.plan.common.enums.PlanOrderStatusEnum;
import com.inventory.middle.interfaces.support.UserContext;
import com.inventory.middle.interfaces.web.plan.dto.IssuePlanOrderReqDTO;
import com.inventory.middle.interfaces.web.plan.dto.ManualPlanOrderCreateReqDTO;
import com.inventory.middle.interfaces.web.plan.dto.PlanOrderIssueDetailPageQueryReqDTO;
import com.inventory.middle.interfaces.web.plan.dto.PlanOrderPageQueryReqDTO;
import com.inventory.middle.interfaces.web.plan.dto.PlanOrderUpdateReqDTO;
import com.inventory.middle.interfaces.web.plan.vo.PageQueryPlanOrderIssueDetailResVO;
import com.inventory.middle.interfaces.web.plan.vo.PlanOrderBaseInfoVO;
import com.inventory.middle.interfaces.web.plan.vo.PlanOrderMaterialInfoVO;
import com.inventory.middle.interfaces.web.plan.vo.PlanOrderPageQueryResVO;
import com.inventory.middle.interfaces.web.plan.vo.PlanOrderQueryResVO;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;

/**
 * Web 层与 Client DTO 间转换。
 */
public final class PlanOrderWebConvertor {

    private static final String[] DATE_PATTERNS = {"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd"};

    private PlanOrderWebConvertor() {
    }

    public static ManualPlanOrderCreateDTO toCreateDTO(ManualPlanOrderCreateReqDTO reqDTO, UserContext userContext) throws ParseException {
        ManualPlanOrderCreateDTO dto = new ManualPlanOrderCreateDTO();
        dto.setMaterialCode(reqDTO.getMaterialCode());
        dto.setMaterialDesc(reqDTO.getMaterialDesc());
        dto.setLogicalPlantNo(reqDTO.getLogicalPlantNo());
        dto.setLogicalPlantName(reqDTO.getLogicalPlantName());
        dto.setPlanOrderQuantity(reqDTO.getPlanOrderQuantity());
        dto.setUnit(reqDTO.getUnit());
        dto.setForecastInventory(reqDTO.getForecastInventory());
        dto.setPlanIssueTime(parseDate(reqDTO.getPlanIssueTime()));
        dto.setPlanReceivingTime(parseDate(reqDTO.getPlanReceivingTime()));
        fillUserInfo(dto, userContext);
        return dto;
    }

    public static PlanOrderDTO toUpdateDTO(PlanOrderUpdateReqDTO reqDTO, UserContext userContext) throws ParseException {
        PlanOrderDTO dto = new PlanOrderDTO();
        dto.setId(reqDTO.getId());
        dto.setMaterialCode(reqDTO.getMaterialCode());
        dto.setMaterialDesc(reqDTO.getMaterialDesc());
        dto.setLogicalPlantNo(reqDTO.getLogicalPlantNo());
        dto.setLogicalPlantName(reqDTO.getLogicalPlantName());
        dto.setPlanOrderQuantity(reqDTO.getPlanOrderQuantity());
        dto.setUnit(reqDTO.getUnit());
        dto.setForecastInventory(reqDTO.getForecastInventory());
        dto.setPlanIssueTime(parseDate(reqDTO.getPlanIssueTime()));
        dto.setPlanReceivingTime(parseDate(reqDTO.getPlanReceivingTime()));
        fillUserInfo(dto, userContext);
        return dto;
    }

    public static PlanOrderIssueReqDTO toIssueDTO(IssuePlanOrderReqDTO reqDTO, UserContext userContext) {
        PlanOrderIssueReqDTO dto = new PlanOrderIssueReqDTO();
        dto.setPlanOrderId(reqDTO.getPlanOrderId());
        dto.setIssueQuantity(reqDTO.getIssueQuantity());
        fillUserInfo(dto, userContext);
        // TODO: 待接入外部下发 token。
        dto.setToken(null);
        return dto;
    }

    public static PlanOrderPageReqDTO toPageDTO(PlanOrderPageQueryReqDTO reqDTO, String tenantId) {
        PlanOrderPageReqDTO dto = new PlanOrderPageReqDTO();
        dto.setOrderNo(reqDTO.getOrderNo());
        dto.setMaxCreateTime(reqDTO.getMaxCreateTime());
        dto.setMinCreateTime(reqDTO.getMinCreateTime());
        dto.setLogicalPlantNo(reqDTO.getLogicalPlantNo());
        dto.setStatus(reqDTO.getStatus());
        dto.setMaterialCode(reqDTO.getMaterialCode());
        dto.setPageNum(reqDTO.getPageNum());
        dto.setPageSize(reqDTO.getPageSize());
        dto.setTenantId(tenantId);
        return dto;
    }

    public static PlanOrderIssueDetailPageReqDTO toIssueDetailPageDTO(PlanOrderIssueDetailPageQueryReqDTO reqDTO, String tenantId) {
        PlanOrderIssueDetailPageReqDTO dto = new PlanOrderIssueDetailPageReqDTO();
        dto.setPlanOrderId(reqDTO.getPlanOrderId());
        dto.setPageNum(reqDTO.getPageNum());
        dto.setPageSize(reqDTO.getPageSize());
        dto.setTenantId(tenantId);
        return dto;
    }

    public static PlanOrderQueryResVO toQueryVO(PlanOrderDTO dto) {
        if (dto == null) {
            return null;
        }
        PlanOrderQueryResVO vo = new PlanOrderQueryResVO();
        vo.setId(dto.getId());
        vo.setDemandInfo(dto.getDemandInfo());

        PlanOrderBaseInfoVO baseInfo = new PlanOrderBaseInfoVO();
        baseInfo.setOrderNo(dto.getOrderNo());
        baseInfo.setCreateTypeCode(dto.getCreateType());
        baseInfo.setCreateTypeDesc(desc(PlanOrderCreateTypeEnum.getByCode(dto.getCreateType())));
        baseInfo.setCreateTime(dto.getCreateTime());
        baseInfo.setPlanIssueTime(dto.getPlanIssueTime());
        baseInfo.setRealIssueTime(dto.getRealIssueTime());
        baseInfo.setOperatorName(dto.getOperatorName());
        baseInfo.setPlanTypeCode(dto.getPlanType());
        baseInfo.setPlanTypeDesc(desc(PlanMaterialParamPlanTypeEnum.getByCode(dto.getPlanType())));
        baseInfo.setStatusCode(dto.getStatus());
        baseInfo.setStatusDesc(desc(PlanOrderStatusEnum.getByCode(dto.getStatus())));
        baseInfo.setConfirmTime(dto.getConfirmTime());
        baseInfo.setPlanReceivingTime(dto.getPlanReceivingTime());
        baseInfo.setRealReceivingTime(dto.getRealReceivingTime());
        baseInfo.setPlannerName(dto.getPlannerName());
        baseInfo.setPlannerId(dto.getPlannerId());
        vo.setBaseInfo(baseInfo);

        PlanOrderMaterialInfoVO materialInfo = new PlanOrderMaterialInfoVO();
        materialInfo.setMaterialCode(dto.getMaterialCode());
        materialInfo.setMaterialDesc(dto.getMaterialDesc());
        materialInfo.setExternalMaterialCode(dto.getExternalMaterialCode());
        materialInfo.setUnit(dto.getUnit());
        materialInfo.setLogicalPlantNo(dto.getLogicalPlantNo());
        materialInfo.setLogicalPlantName(dto.getLogicalPlantName());
        materialInfo.setForecastInventory(dto.getForecastInventory());
        materialInfo.setPlanOrderQuantity(dto.getPlanOrderQuantity());
        materialInfo.setConfirmQuantity(dto.getConfirmQuantity());
        materialInfo.setIssueQuantity(dto.getIssueQuantity());
        materialInfo.setFinishQuantity(dto.getFinishQuantity());
        vo.setMaterialInfo(materialInfo);
        return vo;
    }

    public static PlanOrderPageQueryResVO toPageVO(PlanOrderDTO dto) {
        PlanOrderPageQueryResVO vo = new PlanOrderPageQueryResVO();
        vo.setOrderNo(dto.getOrderNo());
        vo.setMaterialCode(dto.getMaterialCode());
        vo.setMaterialDesc(dto.getMaterialDesc());
        vo.setExternalMaterialCode(dto.getExternalMaterialCode());
        vo.setLogicalPlantNo(dto.getLogicalPlantNo());
        vo.setLogicalPlantName(dto.getLogicalPlantName());
        vo.setPlanOrderQuantity(dto.getPlanOrderQuantity());
        vo.setId(dto.getId());
        vo.setIssueQuantity(dto.getIssueQuantity());
        vo.setUpdateTime(dto.getUpdateTime());
        vo.setCreateType(dto.getCreateType());
        vo.setCreateUser(dto.getOperatorName());
        vo.setStatus(dto.getStatus());
        vo.setPlanCode(dto.getPlanCode());
        return vo;
    }

    public static PageQueryPlanOrderIssueDetailResVO toIssueDetailVO(PlanOrderIssueDetailDTO dto) {
        PageQueryPlanOrderIssueDetailResVO vo = new PageQueryPlanOrderIssueDetailResVO();
        vo.setIssueNo(dto.getIssueNo());
        vo.setPlanOrderId(dto.getPlanOrderId());
        vo.setIssueQuantity(dto.getIssueQuantity());
        vo.setFinishQuantity(dto.getFinishQuantity());
        vo.setCurrentStatus(dto.getCurrentStatus());
        vo.setUpdateTime(dto.getUpdateTime());
        vo.setIssueTime(dto.getCreateTime());
        return vo;
    }

    private static void fillUserInfo(com.inventory.middle.client.plan.BaseDTO dto, UserContext userContext) {
        if (userContext == null) {
            return;
        }
        dto.setTenantId(userContext.getTenantId());
        dto.setUserId(userContext.getUserId());
        dto.setUserName(userContext.getUsername());
    }

    private static java.util.Date parseDate(String value) throws ParseException {
        if (value == null) {
            return null;
        }
        return DateUtils.parseDate(value, DATE_PATTERNS);
    }

    private static String desc(Object enumObj) {
        if (enumObj == null) {
            return null;
        }
        if (enumObj instanceof PlanOrderCreateTypeEnum) {
            return ((PlanOrderCreateTypeEnum) enumObj).getDesc();
        }
        if (enumObj instanceof PlanMaterialParamPlanTypeEnum) {
            return ((PlanMaterialParamPlanTypeEnum) enumObj).getDesc();
        }
        if (enumObj instanceof PlanOrderStatusEnum) {
            return ((PlanOrderStatusEnum) enumObj).getDesc();
        }
        return null;
    }
}
