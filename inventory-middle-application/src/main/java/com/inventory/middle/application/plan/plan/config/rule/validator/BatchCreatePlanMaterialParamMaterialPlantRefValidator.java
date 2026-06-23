package com.inventory.middle.application.plan.plan.config.rule.validator;

import com.alibaba.fastjson.JSON;
import com.inventory.middle.domain.plan.common.enums.ResponseCodeEnum;
import com.inventory.middle.domain.plan.common.rule.IValidator;
import com.inventory.middle.domain.plan.common.rule.ValidateMessage;
import com.inventory.middle.application.plan.plan.config.bo.PlanMaterialParamBatchCreateDetailBO;
import com.inventory.middle.application.plan.plan.config.bo.PlanMaterialParamBatchCreateReqBO;
import com.inventory.middle.application.plan.plan.config.bo.PlanMaterialParameterBO;
import com.inventory.middle.application.plan.support.InventorySupportService;
import com.inventory.middle.application.service.InventoryMaterialApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;


import javax.annotation.Resource;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 物料逻辑仓关系校验器
 * @date 2021/11/1 10:05
 */
@Slf4j
@Component
public class BatchCreatePlanMaterialParamMaterialPlantRefValidator implements IValidator {

    @Resource
    private InventorySupportService inventorySupportService;

    @Resource
    private InventoryMaterialApplicationService inventoryMaterialApplicationService;

    @Override
    public ValidateMessage doValidate(ValidateMessage message) {
        // 校验入参
        PlanMaterialParamBatchCreateReqBO reqBO = (PlanMaterialParamBatchCreateReqBO)message.getT();
        log.info("BatchCreatePlanMaterialParamMaterialPlantRefValidator: " + JSON.toJSONString(reqBO));
        // 校验结果
        List<PlanMaterialParamBatchCreateDetailBO> failedDetails = new ArrayList<>();
        if (CollectionUtils.isEmpty(reqBO.getPlanMaterialParamList())){
            return ValidateMessage.builder().t(reqBO).e(failedDetails).build();
        }
        // 查询逻辑仓关系
        List<String> logicalPlantNos = reqBO.getPlanMaterialParamList().stream().map(PlanMaterialParameterBO :: getLogicalPlantNo).collect(Collectors.toList());
        Map<String, List<String>> refMap = inventoryMaterialApplicationService.listByLogicalPlantNos(logicalPlantNos, reqBO.getTenantId());

        Iterator<PlanMaterialParameterBO> iterator = reqBO.getPlanMaterialParamList().iterator();
        while (iterator.hasNext()) {
            PlanMaterialParameterBO planMaterialParameterBO = iterator.next();
            List<String> materialCodes = refMap.get(planMaterialParameterBO.getLogicalPlantNo());
            if (Objects.isNull(materialCodes) || !materialCodes.contains(planMaterialParameterBO.getMaterialCode())){
                // 校验参数格式
                PlanMaterialParamBatchCreateDetailBO failedBO = new PlanMaterialParamBatchCreateDetailBO();
                failedBO.setMaterialCode(planMaterialParameterBO.getMaterialCode());
                failedBO.setLogicalPlantNo(planMaterialParameterBO.getLogicalPlantNo());
                failedBO.setCreateMessage(ResponseCodeEnum.P_CREATE_PLAN_MATERIAL_PARAM_FAIL_MATERIAL_PLANT_REF_ERROR.getDesc());
                failedBO.setIndex(planMaterialParameterBO.getIndex());
                failedDetails.add(failedBO);
                iterator.remove();
            }
        }
        return ValidateMessage.builder().t(reqBO).e(failedDetails).build();
    }
}