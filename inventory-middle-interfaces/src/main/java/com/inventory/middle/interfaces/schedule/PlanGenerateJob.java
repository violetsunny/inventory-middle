package com.inventory.middle.interfaces.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PlanGenerateJob {

    @Scheduled(cron = "${plan.job.generate.cron:0 0 1 * * ?}")
    public void execute() {
        log.info("PlanGenerateJob start");
        try {
            log.info("PlanGenerateJob success");
        } catch (Exception e) {
            log.error("PlanGenerateJob error", e);
        }
    }
}
