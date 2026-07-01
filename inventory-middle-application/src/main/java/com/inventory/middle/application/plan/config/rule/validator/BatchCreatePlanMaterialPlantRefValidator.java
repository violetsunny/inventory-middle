package com.inventory.middle.application.plan.config.rule.validator;

import com.alibaba.fastjson.JSON;
import com.inventory.middle.domain.common.constants.ResponseCodeEnum;
import com.inventory.middle.domain.plan.common.rule.IValidator;
import com.inventory.middle.domain.plan.common.rule.ValidateMessage;
import com.inventory.middle.application.plan.config.bo.PlanMaterialBO;
import com.inventory.middle.application.plan.config.bo.PlanMaterialBatchCreateDetailBO;
import com.inventory.middle.application.plan.config.bo.PlanMaterialBatchCreateReqBO;
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
 * @description: 校验逻辑仓物料关系
 * @date 2021/11/3 16:38
 */
@Slf4j
@Component
public class BatchCreatePlanMaterialPlantRefValidator  implements IValidator {

    @Resource
    private InventorySupportService inventorySupportService;

    @Resource
    private InventoryMaterialApplicationService inventoryMaterialApplicationService;

    @Override
    public ValidateMessage doValidate(ValidateMessage message) {
        // 校验入参
        PlanMaterialBatchCreateReqBO reqBO = (PlanMaterialBatchCreateReqBO)message.getT();
        log.info("BatchCreatePlanMaterialMaterialPlantRefValidator: " + JSON.toJSONString(reqBO));
        // 校验结果
        List<PlanMaterialBatchCreateDetailBO> failedDetails = new ArrayList<>();
        if (CollectionUtils.isEmpty(reqBO.getPlanMaterialList())){
            return ValidateMessage.builder().t(reqBO).e(failedDetails).build();
        }
        // 查询逻辑仓关系
        List<String> logicalPlantNos = reqBO.getPlanMaterialList().stream().map(PlanMaterialBO:: getLogicalPlantNo).collect(Collectors.toList());
        Map<String, List<String>> refMap = inventoryMaterialApplicationService.listByLogicalPlantNos(logicalPlantNos, reqBO.getTenantId());

        Iterator<PlanMaterialBO> iterator = reqBO.getPlanMaterialList().iterator();
        while (iterator.hasNext()) {
            PlanMaterialBO planMaterialBO = iterator.next();
            List<String> materialCodes = refMap.get(planMaterialBO.getLogicalPlantNo());
            if (Objects.isNull(materialCodes) || !materialCodes.contains(planMaterialBO.getMaterialCode())){
                // 校验参数格式
                PlanMaterialBatchCreateDetailBO failedBO = new PlanMaterialBatchCreateDetailBO();
                failedBO.setMaterialCode(planMaterialBO.getMaterialCode());
                failedBO.setLogicalPlantNo(planMaterialBO.getLogicalPlantNo());
                failedBO.setCreateMessage(ResponseCodeEnum.P_CREATE_PLAN_MATERIAL_PARAM_FAIL_MATERIAL_PLANT_REF_ERROR.getDesc());
                failedBO.setIndex(planMaterialBO.getIndex());
                failedDetails.add(failedBO);
                iterator.remove();
            }
        }
        return ValidateMessage.builder().t(reqBO).e(failedDetails).build();
    }
}