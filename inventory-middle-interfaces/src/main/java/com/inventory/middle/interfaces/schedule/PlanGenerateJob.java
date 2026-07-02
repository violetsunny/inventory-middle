package com.inventory.middle.interfaces.schedule;

import com.inventory.middle.application.plan.calculate.service.PlanGenerateService;
import com.inventory.middle.application.plan.calculate.support.generate.PlanGeneType;
import com.inventory.middle.application.plan.config.bo.PlanBO;
import com.inventory.middle.application.plan.calculate.bo.PlanGenerateRequestBO;
import com.inventory.middle.application.plan.config.bo.PlanMaterialParamQueryReqBO;
import com.inventory.middle.application.plan.config.bo.PlanMaterialParameterBO;
import com.inventory.middle.application.plan.config.bo.PlanQueryByTypeReqBO;
import com.inventory.middle.application.plan.config.enums.CalculateParamsKeyEnum;
import com.inventory.middle.application.plan.config.enums.OrderCalRuleEnum;
import com.inventory.middle.application.plan.config.enums.PlanExecuteTypeEnum;
import com.inventory.middle.application.plan.config.service.PlanConfigService;
import com.inventory.middle.domain.plan.common.bo.MaterialBO;
import com.inventory.middle.domain.plan.common.enums.PlanStatusEnum;
import com.inventory.middle.domain.service.lock.DistributedLockService;
import com.inventory.middle.infra.plan.persistence.dao.MaterialPlanInstanceDao;
import com.inventory.middle.infra.plan.persistence.entity.MaterialPlanInstancePO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Component
public class PlanGenerateJob {

    @Resource
    private PlanConfigService planConfigService;

    @Resource
    private PlanGenerateService planGenerateService;

    @Resource
    private MaterialPlanInstanceDao materialPlanInstanceDao;

    @Resource
    private DistributedLockService distributedLockService;

    private static final String LOCK_KEY = "JOB:PLAN_GENERATE";

    @Scheduled(cron = "${plan.job.generate.cron:0 0 1 * * ?}")
    public void execute() {
        log.info("PlanGenerateJob start");
        try {
            distributedLockService.executeWithLock(100L, TimeUnit.MILLISECONDS, LOCK_KEY, () -> {
                List<PlanBO> plans = findSchedulingPlans();
                for (PlanBO plan : plans) {
                    List<MaterialBO> materials = findSchedulingMaterials(plan);
                    if (CollectionUtils.isNotEmpty(materials)) {
                        PlanGenerateRequestBO request = new PlanGenerateRequestBO();
                        request.setGenerateType(PlanGeneType.JOB);
                        request.setPlanId(plan.getId());
                        request.setTenantId(plan.getTenantId());
                        request.setCoverMaterials(materials);
                        try {
                            planGenerateService.generate(request);
                            log.info("PlanGenerateJob plan generate finish, planId={}", plan.getId());
                        } catch (Exception e) {
                            log.error("PlanGenerateJob plan generate error, planId={}", plan.getId(), e);
                        }
                    }
                }
                return null;
            });
            log.info("PlanGenerateJob success");
        } catch (Exception e) {
            log.error("PlanGenerateJob error", e);
        }
    }

    private List<PlanBO> findSchedulingPlans() {
        PlanQueryByTypeReqBO condition = new PlanQueryByTypeReqBO();
        condition.setPlanType(PlanExecuteTypeEnum.AUTOMATIC.getCode());
        List<PlanBO> candidatePlans = planConfigService.queryPlanByType(condition);
        if (CollectionUtils.isEmpty(candidatePlans)) {
            return java.util.Collections.emptyList();
        }
        return candidatePlans.stream()
                .filter(p -> PlanStatusEnum.ON.getCode().equals(p.getStatus()))
                .filter(p -> {
                    String orderCalRule = MapUtils.getString(p.getPlanCalculateParams(),
                            CalculateParamsKeyEnum.ORDER_CAL_RULE.getCode());
                    return OrderCalRuleEnum.isCycleTime(orderCalRule);
                })
                .collect(Collectors.toList());
    }

    private List<MaterialBO> findSchedulingMaterials(PlanBO plan) {
        List<MaterialBO> materials = planConfigService.queryMaterialsByPlan(plan);
        if (CollectionUtils.isEmpty(materials)) {
            return java.util.Collections.emptyList();
        }
        return materials.stream()
                .filter(m -> supports(m, plan))
                .collect(Collectors.toList());
    }

    private boolean supports(MaterialBO material, PlanBO plan) {
        PlanMaterialParamQueryReqBO request = new PlanMaterialParamQueryReqBO();
        request.setTenantId(material.getTenantId());
        request.setLogicalPlantNo(material.getLogicalPlantNo());
        request.setMaterialCode(material.getMaterialCode());
        PlanMaterialParameterBO param = planConfigService.queryByMaterialCodeAndLogicalPlantNo(request);
        if (param == null) {
            return false;
        }
        Integer orderCycleTime = param.getOrderCycleTime();
        MaterialPlanInstancePO materialPlanInstance = materialPlanInstanceDao.selectLatestByMaterial(
                material.getMaterialCode(), material.getLogicalPlantNo(),
                material.getTenantId(), PlanGeneType.JOB.getCode());
        if (materialPlanInstance == null) {
            return true;
        }
        return !LocalDate.now().isBefore(
                materialPlanInstance.getCreateTime().toInstant()
                        .atZone(java.time.ZoneId.systemDefault()).toLocalDate()
                        .plusDays(orderCycleTime));
    }

}
