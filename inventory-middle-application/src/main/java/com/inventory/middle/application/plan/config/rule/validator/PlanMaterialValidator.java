package com.inventory.middle.application.plan.config.rule.validator;

import com.inventory.middle.domain.plan.common.constants.CommonConstants;
import com.inventory.middle.domain.plan.common.enums.PlanCoverMaterialTypeEnum;
import com.inventory.middle.domain.plan.common.enums.ResponseCodeEnum;
import com.inventory.middle.domain.plan.common.ex.SpmException;
import com.inventory.middle.application.plan.config.bo.PlanBO;
import com.inventory.middle.application.plan.config.bo.PlanMaterialBO;
import com.inventory.middle.application.plan.config.convertor.PlanConfigConverter;
import com.inventory.middle.domain.plan.common.rule.IValidator;
import com.inventory.middle.domain.plan.common.rule.ValidateMessage;
import com.inventory.middle.infra.plan.persistence.dao.PlanConfigDao;
import com.inventory.middle.infra.plan.persistence.condition.PlanQueryByCoverMaterialTypeCondition;
import com.inventory.middle.infra.plan.persistence.entity.PlanMaterialPO;
import com.inventory.middle.infra.plan.persistence.entity.PlanPO;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 计划方案 物料清单校验器
 * @date 2021/11/5 10:36
 */
@Component
@Slf4j
public class PlanMaterialValidator implements IValidator {

    @Resource
    private PlanConfigDao planConfigDao;

    @Override
    public ValidateMessage doValidate(ValidateMessage message) {
        log.info("enter PlanMaterialValidator doValidate() ");
        // 校验入参
        PlanBO planBO = (PlanBO) message.getT();
        // 只有指定物料的计划才校验
        if (planBO.getCoverMaterialType().equals(PlanCoverMaterialTypeEnum.APPOINT.getCode())) {
            log.info("enter PlanMaterialValidator planConfigDao.queryPlanMaterialByPlanIds()");
            List<PlanMaterialPO> planMaterialPOS = planConfigDao.queryPlanMaterialByPlanIds(Lists.newArrayList(planBO.getId()));
            log.info("exit PlanMaterialValidator planConfigDao.queryPlanMaterialByPlanIds()");
            // 物料清单不能为空
            if (CollectionUtils.isEmpty(planMaterialPOS)) {
                throw new SpmException(ResponseCodeEnum.P_CHANGE_PLAN_STATUS_FAIL_PLAN_MATERIAL_IS_NULL.getCode(), ResponseCodeEnum.P_CHANGE_PLAN_STATUS_FAIL_PLAN_MATERIAL_IS_NULL.getDesc());
            }
            // 校验是否存在冲突
            List<PlanMaterialBO> planMaterialBOS = planMaterialPOS.stream().map(d -> PlanConfigConverter.convertPlanMaterialPO2BO(d)).collect(Collectors.toList());
            List<PlanMaterialBO> failedDetails = checkCoverAppointMaterialPlan(planBO, planMaterialBOS);
            if (!CollectionUtils.isEmpty(failedDetails)) {
                // 错误信息封装
                StringBuffer msg = new StringBuffer();
                for (PlanMaterialBO planMaterialBO : failedDetails) {
                    msg.append(planMaterialBO.getMaterialCode()).append(CommonConstants.DELIMITER_COMMA);
                }
                msg.append(ResponseCodeEnum.P_CREATE_PLAN_MATERIAL_FAIL_MATERIAL_CODE_IN_EXEC.getDesc());
                throw new SpmException(ResponseCodeEnum.P_CREATE_PLAN_MATERIAL_FAIL_MATERIAL_CODE_IN_EXEC.getCode(), msg.toString());
            }

        }
        log.info("exit PlanMaterialValidator doValidate() ");
        return ValidateMessage.builder().t(planBO).e(null).build();
    }

    private List<PlanMaterialBO> checkCoverAppointMaterialPlan(PlanBO planBO, List<PlanMaterialBO> planMaterials) {
        log.info("enter PlanMaterialValidator checkCoverAppointMaterialPlan()");
        // 失败列表
        List<PlanMaterialBO> failedDetails = Lists.newArrayList();

        // 查询指定部分物料的在执行计划
        PlanQueryByCoverMaterialTypeCondition condition = PlanQueryByCoverMaterialTypeCondition.builder()
                .coverMaterialType(PlanCoverMaterialTypeEnum.APPOINT.getCode())
                .tenantId(planBO.getTenantId()).build();
        List<PlanPO> plans = planConfigDao.queryPlanByCoverMaterialType(condition);
        if (CollectionUtils.isEmpty(plans)) {
            return failedDetails;
        }
        // 因为可以覆盖当前计划的物料清单 所以需要去掉本计划的物料清单
        plans = plans.stream().filter(d -> !d.getId().equals(planBO.getId())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(plans)) {
            return failedDetails;
        }
        // 根据计划Id去查出所有的物料清单
        List<Long> planIds = plans.stream().map(PlanPO::getId).collect(Collectors.toList());
        List<PlanMaterialPO> inExecPlanMaterials = planConfigDao.queryPlanMaterialByPlanIds(planIds);
        if (CollectionUtils.isEmpty(planMaterials)) {
            return failedDetails;
        }
        // 转换成map最后去匹配 key： materialCode + logicalPlantNo | value : planId
        Map<String, Long> inExecMaterialMap = inExecPlanMaterials.stream().collect(Collectors.toMap(k -> k.getMaterialCode() + k.getLogicalPlantNo(), PlanMaterialPO::getSourceId, (key1, key2) -> key2));
        // 如果涵盖了上传的逻辑仓信息则校验失败
        Iterator<PlanMaterialBO> iterator = planMaterials.iterator();
        while (iterator.hasNext()) {
            PlanMaterialBO planMaterialBO = iterator.next();
            String key = planMaterialBO.getMaterialCode() + planMaterialBO.getLogicalPlantNo();
            if (!Objects.isNull(inExecMaterialMap.get(key))) {
                failedDetails.add(planMaterialBO);
                iterator.remove();
            }
        }
        log.info("exit PlanMaterialValidator checkCoverAppointMaterialPlan()");
        return failedDetails;
    }
}