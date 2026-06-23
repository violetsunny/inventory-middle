package com.inventory.middle.application.plan.config.rule.validator;

import com.inventory.middle.domain.plan.common.enums.PlanCoverMaterialTypeEnum;
import com.inventory.middle.domain.plan.common.enums.ResponseCodeEnum;
import com.inventory.middle.domain.plan.common.ex.Ex;
import com.inventory.middle.domain.plan.common.rule.IValidator;
import com.inventory.middle.domain.plan.common.rule.ValidateMessage;
import com.inventory.middle.application.plan.config.bo.PlanBO;
import com.inventory.middle.infra.plan.persistence.dao.PlanConfigDao;
import com.inventory.middle.infra.plan.persistence.entity.PlanPO;
import com.inventory.middle.client.plan.dto.inventory.InvPlantBO;
import com.inventory.middle.application.plan.support.InventorySupportService;
import com.inventory.middle.application.service.LogicalPlantQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;


import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 校验逻辑仓是已经在执行 校验器
 * @date 2021/11/5 10:27
 */
@Component
@Slf4j
public class PlanLogicalPlantInExecValidator implements IValidator {
    @Resource
    private PlanConfigDao planConfigDao;

    @Resource
    private InventorySupportService inventorySupportService;

    @Resource
    private LogicalPlantQueryService logicalPlantQueryService;

    @Override
    public ValidateMessage doValidate(ValidateMessage message) {
        log.info("Enter PlanLogicalPlantInExecValidator doValidate()");
        // 校验入参
        PlanBO planBO = (PlanBO) message.getT();
        // 校验逻辑仓是否冲突
        List<String> logicalPlantNos = checkLogicalPlant(planBO);

        if (!CollectionUtils.isEmpty(logicalPlantNos)) {
            throw Ex.of(ResponseCodeEnum.P_SAVE_PLAN_FAIL_LOGICAL_PLANT_IN_CONFLICT);
        }
        log.info("exit PlanLogicalPlantInExecValidator doValidate()");
        return ValidateMessage.builder().t(planBO).e(null).build();
    }

    /**
     * 封装结果信息
     * @param logicalPlantNos
     * @param tenantId
     * @return
     */
    private String assembleResultMsg(List<String> logicalPlantNos, String tenantId) {
        // 返回信息封装
        StringBuffer msg = new StringBuffer();
        // 查询逻辑仓信息
        log.info("Enter PlanLogicalPlantInExecValidator query remoteInventoryCenterService listLogicalPlants()");
        List<InvPlantBO> plants = logicalPlantQueryService.list(tenantId);
        log.info("exit PlanLogicalPlantInExecValidator query remoteInventoryCenterService listLogicalPlants()");
        Map<String, String> logicalPlantMap = null;
        if (!CollectionUtils.isEmpty(plants)) {
            logicalPlantMap = plants.stream().collect(Collectors.toMap(InvPlantBO::getPlantCode, InvPlantBO::getPlantName, (key1, key2) -> key2));
        }

        for (String logicalPlantNo : logicalPlantNos) {

            if (!Objects.isNull(logicalPlantMap) && !Objects.isNull(logicalPlantMap.get(logicalPlantNo))) {
                String logicalPlantName = logicalPlantMap.get(logicalPlantNo);
                msg.append(logicalPlantName).append(",");
            } else {
                msg.append(logicalPlantNo).append(",");
            }
        }
        msg.append(ResponseCodeEnum.P_SAVE_PLAN_FAIL_LOGICAL_PLANT_IN_CONFLICT.getDesc());

        return msg.toString();
    }

    /**
     * 校验选择的逻辑仓是否在正在执行的计划方案中
     * 存在返回false 不存在返回true
     *
     * @param planBO
     * @return
     */
    private List<String> checkLogicalPlant(PlanBO planBO) {
        log.info("enter PlanLogicalPlantInExecValidator checkLogicalPlant()");

        List<String> logicalPlantNos = new ArrayList<>();

        // 查出租户下的所有在执行的计划方案
        List<PlanPO> plans = planConfigDao.queryByTenantId(planBO.getTenantId());

        for (String logicalPlantNo : planBO.getCoverLogicalPlantNos()) {

            // 放到外面
            plans = plans.stream().filter(d -> !d.getId().equals(planBO.getId()) &&
                    d.getCoverLogicalPlant().contains(logicalPlantNo)).collect(Collectors.toList());

            // 如果是覆盖全部物料的计划 只要该逻辑仓正在执行就不能保存
            if (PlanCoverMaterialTypeEnum.ALL.getCode().equals(planBO.getCoverMaterialType())) {
                if (!CollectionUtils.isEmpty(plans)) {
                    logicalPlantNos.add(logicalPlantNo);
                }
            }
            // 如果是覆盖指定物料的计划 只要不存在覆盖全部物料的在执行计划就行
            if (PlanCoverMaterialTypeEnum.APPOINT.getCode().equals(planBO.getCoverMaterialType())) {
                List<PlanPO> coverAllPlans = plans.stream().filter(d -> d.getCoverMaterialType().equals(PlanCoverMaterialTypeEnum.ALL.getCode())).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(coverAllPlans)) {
                    logicalPlantNos.add(logicalPlantNo);
                }
            }
        }
        log.info("exit PlanLogicalPlantInExecValidator checkLogicalPlant()");
        return logicalPlantNos;
    }
}