package com.inventory.middle.application.plan.config.rule.validator;

import com.inventory.middle.domain.plan.common.constants.CommonConstants;
import com.inventory.middle.domain.plan.common.enums.PlanCoverMaterialTypeEnum;
import com.inventory.middle.domain.plan.common.enums.ResponseCodeEnum;
import com.inventory.middle.application.plan.config.bo.PlanMaterialBO;
import com.inventory.middle.application.plan.config.bo.PlanMaterialBatchCreateDetailBO;
import com.inventory.middle.application.plan.config.bo.PlanMaterialBatchCreateReqBO;
import com.inventory.middle.application.plan.config.convertor.PlanConfigConverter;
import com.inventory.middle.domain.plan.common.rule.IValidator;
import com.inventory.middle.domain.plan.common.rule.ValidateMessage;
import com.inventory.middle.infra.plan.persistence.dao.PlanConfigDao;
import com.inventory.middle.infra.plan.persistence.condition.PlanQueryByCoverMaterialTypeCondition;
import com.inventory.middle.infra.plan.persistence.entity.PlanMaterialPO;
import com.inventory.middle.infra.plan.persistence.entity.PlanPO;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;


import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 计划物料清单 校验是否在执行中
 * @date 2021/10/2 21:46
 */
@Component
public class BatchCreatePlanMaterialInExecValidator implements IValidator {

    @Resource
    private PlanConfigDao planConfigDao;

    @Override
    public ValidateMessage doValidate(ValidateMessage message) {
        //校验入参
        PlanMaterialBatchCreateReqBO reqBO = (PlanMaterialBatchCreateReqBO) message.getT();
        // 校验结果
        List<PlanMaterialBatchCreateDetailBO> failedDetails = new ArrayList<>();
        // 1.校验涵盖所有物料类型的计划
        failedDetails.addAll(checkCoverAllMaterialPlan(reqBO));
        // 2.校验涵盖指定物料类型的计划
        failedDetails.addAll(checkCoverAppointMaterialPlan(reqBO));
        return ValidateMessage.builder().t(reqBO).e(failedDetails).build();
    }

    /**
     * 校验指定物料的计划方案
     *
     * @param reqBO
     * @return
     */
    private List<PlanMaterialBatchCreateDetailBO> checkCoverAppointMaterialPlan(PlanMaterialBatchCreateReqBO reqBO) {
        // 失败列表
        List<PlanMaterialBatchCreateDetailBO> failedDetails = new ArrayList<>();
        if (CollectionUtils.isEmpty(reqBO.getPlanMaterialList())) {
            return failedDetails;
        }
        // 查询指定部分物料的在执行计划
        PlanQueryByCoverMaterialTypeCondition condition = PlanQueryByCoverMaterialTypeCondition.builder()
                .coverMaterialType(PlanCoverMaterialTypeEnum.APPOINT.getCode())
                .tenantId(reqBO.getTenantId()).build();
        List<PlanPO> plans = planConfigDao.queryPlanByCoverMaterialType(condition);
        if (CollectionUtils.isEmpty(plans)) {
            return failedDetails;
        }
        // 因为可以覆盖当前计划的物料清单 所以需要去掉本计划的物料清单
        plans = plans.stream().filter(d -> !d.getId().equals(reqBO.getPlanId())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(plans)) {
            return failedDetails;
        }
        // 根据计划Id去查出所有的物料清单
        List<Long> planIds = plans.stream().map(PlanPO::getId).collect(Collectors.toList());
        List<PlanMaterialPO> planMaterials = planConfigDao.queryPlanMaterialByPlanIds(planIds);
        if (CollectionUtils.isEmpty(planMaterials)) {
            return failedDetails;
        }
        // 转换成map最后去匹配 key： materialCode + logicalPlantNo | value : planId
        Map<String, Long> inExecMaterialMap = planMaterials.stream().collect(Collectors.toMap(k -> k.getMaterialCode() + k.getLogicalPlantNo(), PlanMaterialPO::getSourceId, (key1, key2) -> key2));
        // 如果涵盖了上传的逻辑仓信息则校验失败
        Iterator<PlanMaterialBO> iterator = reqBO.getPlanMaterialList().iterator();
        while (iterator.hasNext()) {
            PlanMaterialBO planMaterialBO = iterator.next();
            String key = planMaterialBO.getMaterialCode() + planMaterialBO.getLogicalPlantNo();
            if (!Objects.isNull(inExecMaterialMap.get(key))) {
                PlanMaterialBatchCreateDetailBO failedBO = PlanConfigConverter.convertPlanMaterialBO2CreateDetail(planMaterialBO, ResponseCodeEnum.P_CREATE_PLAN_MATERIAL_FAIL_MATERIAL_CODE_IN_EXEC.getDesc());
                failedDetails.add(failedBO);
                iterator.remove();
            }
        }
        return failedDetails;
    }

    /**
     * 校验全部物料的计划方案
     *
     * @param reqBO
     * @return
     */
    private List<PlanMaterialBatchCreateDetailBO> checkCoverAllMaterialPlan(PlanMaterialBatchCreateReqBO reqBO) {
        // 失败列表
        List<PlanMaterialBatchCreateDetailBO> failedDetails = new ArrayList<>();
        if (CollectionUtils.isEmpty(reqBO.getPlanMaterialList())) {
            return failedDetails;
        }
        // 查询指定全部物料的在执行计划
        PlanQueryByCoverMaterialTypeCondition condition = PlanQueryByCoverMaterialTypeCondition.builder()
                .coverMaterialType(PlanCoverMaterialTypeEnum.ALL.getCode())
                .tenantId(reqBO.getTenantId()).build();
        List<PlanPO> plans = planConfigDao.queryPlanByCoverMaterialType(condition);
        // 因为可以覆盖当前计划的物料清单 所以需要去掉本计划的物料清单
        plans = plans.stream().filter(d -> !d.getId().equals(reqBO.getPlanId())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(plans)) {
            return failedDetails;
        }
        // 得到所有在执行计划的逻辑仓信息
        List<String> logicalPlantInExec = new ArrayList<>();
        List<String> logicalPlantMessages = plans.stream().map(PlanPO::getCoverLogicalPlant).collect(Collectors.toList());
        for (String logicalPlantMessage : logicalPlantMessages) {
            List<String> logicalPlantNos = Arrays.asList(logicalPlantMessage.split(CommonConstants.DELIMITER_COMMA));
            logicalPlantInExec.addAll(logicalPlantNos);
        }
        // 如果涵盖了上传的逻辑仓信息则校验失败
        Iterator<PlanMaterialBO> iterator = reqBO.getPlanMaterialList().iterator();
        while (iterator.hasNext()) {
            PlanMaterialBO planMaterialBO = iterator.next();
            if (logicalPlantInExec.contains(planMaterialBO.getLogicalPlantNo())) {
                String errorMsg = ResponseCodeEnum.P_CREATE_PLAN_MATERIAL_FAIL_MATERIAL_CODE_IN_EXEC.getDesc();
                PlanMaterialBatchCreateDetailBO failedBO = PlanConfigConverter.convertPlanMaterialBO2CreateDetail(planMaterialBO, errorMsg);
                failedDetails.add(failedBO);
                iterator.remove();
            }
        }
        return failedDetails;
    }
}