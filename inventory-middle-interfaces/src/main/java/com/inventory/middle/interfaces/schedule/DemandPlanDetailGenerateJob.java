package com.inventory.middle.interfaces.schedule;

import com.inventory.middle.application.plan.demand.service.DemandPlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class DemandPlanDetailGenerateJob {

    @Resource
    private DemandPlanService demandPlanService;

    @Scheduled(cron = "${plan.job.demandPlanDetailGenerate.cron:0 0 2 * * ?}")
    public void execute() {
        log.info("DemandPlanDetailGenerateJob start");
        try {
            demandPlanService.generateData(null);
            log.info("DemandPlanDetailGenerateJob success");
        } catch (Exception e) {
            log.error("DemandPlanDetailGenerateJob error", e);
        }
    }
}
