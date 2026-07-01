package com.inventory.middle.interfaces.schedule;

import com.inventory.middle.application.plan.dss.PlanDemandSupplyStockApplicationService;
import com.inventory.middle.infra.lock.RedissonDistributedLockService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class PlanDemandSupplyStockGenerateJob {

    @Resource
    private PlanDemandSupplyStockApplicationService planDemandSupplyStockApplicationService;

    @Resource
    private RedissonDistributedLockService redissonDistributedLockService;

    private static final String LOCK_KEY = "JOB:PLAN_DEMAND_SUPPLY_STOCK_GENERATE";

    @Scheduled(cron = "${plan.job.demandSupplyStockGenerate.cron:0 30 1 * * ?}")
    public void execute() {
        log.info("PlanDemandSupplyStockGenerateJob start");
        try {
            redissonDistributedLockService.executeWithLock(100L, TimeUnit.MILLISECONDS, LOCK_KEY, () -> {
                planDemandSupplyStockApplicationService.generateData(null, null, null);
                return null;
            });
            log.info("PlanDemandSupplyStockGenerateJob success");
        } catch (Exception e) {
            log.error("PlanDemandSupplyStockGenerateJob error", e);
        }
    }
}
