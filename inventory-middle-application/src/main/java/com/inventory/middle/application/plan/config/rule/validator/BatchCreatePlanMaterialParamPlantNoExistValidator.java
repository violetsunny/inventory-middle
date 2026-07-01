package com.inventory.middle.application.plan.config.rule.validator;

import com.alibaba.fastjson.JSON;
import com.inventory.middle.domain.common.constants.ResponseCodeEnum;
import com.inventory.middle.domain.plan.common.rule.IValidator;
import com.inventory.middle.domain.plan.common.rule.ValidateMessage;
import com.inventory.middle.application.plan.config.bo.PlanMaterialParamBatchCreateDetailBO;
import com.inventory.middle.application.plan.config.bo.PlanMaterialParamBatchCreateReqBO;
import com.inventory.middle.application.plan.config.bo.PlanMaterialParameterBO;
import com.inventory.middle.application.plan.config.convertor.PlanConfigConverter;
import com.inventory.middle.domain.plan.common.enums.PlanMaterialParamPlanTypeEnum;
import com.inventory.middle.client.plan.dto.inventory.InvPlantBO;
import com.inventory.middle.application.plan.support.InventorySupportService;
import com.inventory.middle.application.service.LogicalPlantQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.apache.commons.lang3.StringUtils;


import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 批量插入校验逻辑仓（调拨逻辑仓及本地逻辑仓）是否存在及是否有效
 *
 * @author caosheng
 * @date 2021/10/19
 */
@Component
@Slf4j
public class BatchCreatePlanMaterialParamPlantNoExistValidator implements IValidator {

    @Resource
    private InventorySupportService inventorySupportService;

    @Resource
    private LogicalPlantQueryService logicalPlantQueryService;


    @Override
    public ValidateMessage doValidate(ValidateMessage message) {
        // 校验入参
        PlanMaterialParamBatchCreateReqBO reqBO = (PlanMaterialParamBatchCreateReqBO) message.getT();
        log.info("BatchCreatePlanMaterialParamPlantNoExistValidator: " + JSON.toJSONString(reqBO));
        // 校验结果
        List<PlanMaterialParamBatchCreateDetailBO> failedDetails = new ArrayList<>();
        if (CollectionUtils.isEmpty(reqBO.getPlanMaterialParamList())) {
            return ValidateMessage.builder().t(reqBO).e(failedDetails).build();
        }
        // 查询租户下逻辑仓
        Map<String, InvPlantBO> logicalPlantMap = queryLogicalPlantByTenantId(reqBO.getTenantId());
        List<PlanMaterialParameterBO> boListTemp = new ArrayList<>();
        Iterator<PlanMaterialParameterBO> iterator = reqBO.getPlanMaterialParamList().iterator();
        while (iterator.hasNext()) {
            PlanMaterialParameterBO planMaterialParameterBO = iterator.next();
            // 检查逻辑仓（源头逻辑仓及本地逻辑仓）及是否关联BOM单
            String checkParamMessage = checkPlanNo(planMaterialParameterBO, logicalPlantMap);
            if (StringUtils.isNotBlank(checkParamMessage)) {
                PlanMaterialParamBatchCreateDetailBO failedBO = new PlanMaterialParamBatchCreateDetailBO();
                failedBO.setMaterialCode(planMaterialParameterBO.getMaterialCode());
                failedBO.setLogicalPlantNo(planMaterialParameterBO.getLogicalPlantNo());
                failedBO.setCreateMessage(checkParamMessage);
                failedBO.setIndex(planMaterialParameterBO.getIndex());
                failedDetails.add(failedBO);
                iterator.remove();
            } else {
                PlanMaterialParameterBO boTemp = PlanConfigConverter.convertPlanMaterialParameterBO2BO(planMaterialParameterBO, logicalPlantMap, null);
                boListTemp.add(boTemp);
            }
        }
        reqBO.setPlanMaterialParamList(boListTemp);
        return ValidateMessage.builder().t(reqBO).e(failedDetails).build();
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
     * @param maps                    maps
     * @return string
     */
    private String checkPlanNo(PlanMaterialParameterBO planMaterialParameterBO, Map<String, InvPlantBO> maps) {

        // 检查逻辑仓是否有效
        if (StringUtils.isNotBlank(planMaterialParameterBO.getLogicalPlantNo()) && !maps.containsKey(planMaterialParameterBO.getLogicalPlantNo())) {
            return ResponseCodeEnum.P_CREATE_PLAN_MATERIAL_PARAM_FAIL_LOGICAL_PLANT_NO_IS_NOT_EXIST.getDesc();
        }
        // 如果计划类型是调拨，检查调拨源头逻辑仓是否存在、有效
        String transferLogicalPlantNo = planMaterialParameterBO.getTransferLogicalPlantNo();
        if (StringUtils.isNotBlank(transferLogicalPlantNo) && !maps.containsKey(transferLogicalPlantNo)) {
            return ResponseCodeEnum.P_CREATE_PLAN_MATERIAL_PARAM_FAIL_TRANSFER_LOGICAL_PLANT_NO_IS_NOT_EXIST.getDesc();
        }
        if (PlanMaterialParamPlanTypeEnum.TRANSFER.getCode().equals(planMaterialParameterBO.getPlanTypeCode())) {
            // 必填项 非空检查
            if (StringUtils.isBlank(transferLogicalPlantNo)) {
                return ResponseCodeEnum.P_CREATE_PLAN_MATERIAL_PARAM_FAIL_TRANSFER_LOGICAL_PLANT_NO_IS_NOT_EMPTY.getDesc();
            }
        } else {
            if (StringUtils.isNotBlank(transferLogicalPlantNo)) {
                return ResponseCodeEnum.P_CREATE_PLAN_MATERIAL_PARAM_FAIL_TRANSFER_LOGICAL_PLANT_NO_IS_LIMIT.getDesc();
            }
        }
        return null;
    }
}