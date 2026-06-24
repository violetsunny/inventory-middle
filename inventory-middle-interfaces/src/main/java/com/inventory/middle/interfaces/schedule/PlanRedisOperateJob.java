package com.inventory.middle.interfaces.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PlanRedisOperateJob {

    @Scheduled(cron = "${plan.job.planRedisOperate.cron:0 0 4 * * ?}")
    public void execute() {
        log.info("PlanRedisOperateJob start");
        try {
            log.info("PlanRedisOperateJob success");
        } catch (Exception e) {
            log.error("PlanRedisOperateJob error", e);
        }
    }
}
