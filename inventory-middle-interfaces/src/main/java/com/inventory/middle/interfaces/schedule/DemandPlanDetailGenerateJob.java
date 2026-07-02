package com.inventory.middle.interfaces.schedule;

import com.inventory.middle.application.plan.demand.service.DemandPlanService;
import com.inventory.middle.domain.service.lock.DistributedLockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class DemandPlanDetailGenerateJob {

    @Resource
    private DemandPlanService demandPlanService;

    @Resource
    private DistributedLockService distributedLockService;

    private static final String LOCK_KEY = "JOB:DEMAND_PLAN_DETAIL_GENERATE";

    @Scheduled(cron = "${plan.job.demandPlanDetailGenerate.cron:0 0 2 * * ?}")
    public void execute() {
        log.info("DemandPlanDetailGenerateJob start");
        try {
            distributedLockService.executeWithLock(100L, TimeUnit.MILLISECONDS, LOCK_KEY, () -> {
                demandPlanService.generateData(null);
                return null;
            });
            log.info("DemandPlanDetailGenerateJob success");
        } catch (Exception e) {
            log.error("DemandPlanDetailGenerateJob error", e);
        }
    }
}
