package com.inventory.middle.interfaces.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PlanDemandSupplyStockGenerateJob {

    @Scheduled(cron = "${plan.job.demandSupplyStockGenerate.cron:0 30 1 * * ?}")
    public void execute() {
        log.info("PlanDemandSupplyStockGenerateJob start");
        try {
            log.info("PlanDemandSupplyStockGenerateJob success");
        } catch (Exception e) {
            log.error("PlanDemandSupplyStockGenerateJob error", e);
        }
    }
}
