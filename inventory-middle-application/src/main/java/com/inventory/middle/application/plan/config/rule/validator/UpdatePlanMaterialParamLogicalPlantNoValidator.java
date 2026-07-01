package com.inventory.middle.application.plan.config.rule.validator;

import com.inventory.middle.domain.common.constants.ResponseCodeEnum;
import com.inventory.middle.domain.plan.common.ex.Ex;
import com.inventory.middle.domain.plan.common.rule.IValidator;
import com.inventory.middle.domain.plan.common.rule.ValidateMessage;
import com.inventory.middle.application.plan.config.bo.PlanMaterialParameterBO;
import com.inventory.middle.domain.plan.common.enums.PlanMaterialParamPlanTypeEnum;
import com.inventory.middle.client.plan.dto.inventory.InvPlantBO;
import com.inventory.middle.application.plan.support.InventorySupportService;
import com.inventory.middle.application.service.LogicalPlantQueryService;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 批量插入校验逻辑仓（调拨逻辑仓及本地逻辑仓）是否存在及是否有效
 *
 * @author caosheng
 * @date 2021/10/26
 */
@Component
public class UpdatePlanMaterialParamLogicalPlantNoValidator implements IValidator {

    @Resource
    private InventorySupportService inventorySupportService;

    @Resource
    private LogicalPlantQueryService logicalPlantQueryService;

    @Override
    public ValidateMessage doValidate(ValidateMessage message) {
        // 校验入参
        PlanMaterialParameterBO planMaterialParameterBO = (PlanMaterialParameterBO) message.getT();
        // 查询租户下逻辑仓
        Map<String, InvPlantBO> logicalPlantMap = queryLogicalPlantByTenantId(planMaterialParameterBO.getTenantId());
        ResponseCodeEnum responseCodeEnum;
        if (!CollectionUtils.isEmpty(logicalPlantMap)) {
            // 检查逻辑仓（源头逻辑仓及本地逻辑仓）及是否关联BOM单
            responseCodeEnum = checkPlanNoAndRelatedBOM(planMaterialParameterBO, logicalPlantMap);
            // 根据逻辑仓编码修改逻辑仓名称
        } else {
            responseCodeEnum = ResponseCodeEnum.P_CREATE_PLAN_MATERIAL_PARAM_FAIL_TENANT_WITHOUT_LOGICAL_PLANT_NO;
        }
        if (!Objects.isNull(responseCodeEnum)) {
            throw Ex.of(responseCodeEnum.getCode(), responseCodeEnum.getDesc());
        }

        return ValidateMessage.builder().t(planMaterialParameterBO).e(null).build();
    }

    private Map<String, InvPlantBO> queryLogicalPlantByTenantId(String tenantId) {
        Map<String, InvPlantBO> logicalPlantMap = null;
        // 获取当前租户下所有逻辑仓
        List<InvPlantBO> plants = logicalPlantQueryService.list(tenantId);
        if (!CollectionUtils.isEmpty(plants)) {
            logicalPlantMap = plants.stream().collect(Collectors.toMap(InvPlantBO::getPlantCode, Function.identity()));
        }
        return logicalPlantMap;
    }

    /**
     * 检查逻辑仓及是否关联BOM
     *
     * @param planMaterialParameterBO planMaterialParameterBO
     * @param logicalPlantMap         logicalPlantMap
     * @return string
     */
    private ResponseCodeEnum checkPlanNoAndRelatedBOM(PlanMaterialParameterBO planMaterialParameterBO, Map<String, InvPlantBO> logicalPlantMap) {
        // 检查逻辑仓是否有效
        if (StringUtils.isNotBlank(planMaterialParameterBO.getLogicalPlantNo()) && !logicalPlantMap.containsKey(planMaterialParameterBO.getLogicalPlantNo())) {
            return ResponseCodeEnum.P_UPDATE_PLAN_MATERIAL_PARAM_FAIL_LOGICAL_PLANT_NO_IS_NOT_EXIST;
        }
        Integer planTypeCode = planMaterialParameterBO.getPlanTypeCode();
        String transferLogicalPlantNo = planMaterialParameterBO.getTransferLogicalPlantNo();
        // 源头逻辑仓非空，检查源头逻辑仓有效性
        if (StringUtils.isNotBlank(transferLogicalPlantNo) && !logicalPlantMap.containsKey(transferLogicalPlantNo)) {
            return ResponseCodeEnum.P_UPDATE_PLAN_MATERIAL_PARAM_FAIL_TRANSFER_LOGICAL_PLANT_NO_IS_NOT_EXIST;
        }
        // 如果计划类型是调拨，检查调拨源头逻辑仓是否存在、有效
        if (PlanMaterialParamPlanTypeEnum.TRANSFER.getCode().equals(planTypeCode)) {
            // 必填项 非空检查
            if (StringUtils.isBlank(transferLogicalPlantNo)) {
                return ResponseCodeEnum.P_UPDATE_PLAN_MATERIAL_PARAM_FAIL_TRANSFER_LOGICAL_PLANT_NO_IS_NOT_EMPTY;
            }
        }
        return null;
    }
}