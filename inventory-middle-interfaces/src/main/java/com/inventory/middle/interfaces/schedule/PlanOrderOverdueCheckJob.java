package com.inventory.middle.interfaces.schedule;

import com.inventory.middle.application.plan.order.service.PlanOrderApplicationService;
import com.inventory.middle.infra.lock.RedissonDistributedLockService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class PlanOrderOverdueCheckJob {

    @Resource
    private PlanOrderApplicationService planOrderApplicationService;

    @Resource
    private RedissonDistributedLockService redissonDistributedLockService;

    private static final String LOCK_KEY = "JOB:PLAN_ORDER_OVERDUE_CHECK";

    @Scheduled(cron = "${plan.job.planOrderOverdueCheck.cron:0 0 3 * * ?}")
    public void execute() {
        log.info("PlanOrderOverdueCheckJob start");
        try {
            redissonDistributedLockService.executeWithLock(100L, TimeUnit.MILLISECONDS, LOCK_KEY, () -> {
                List<Long> overdueOrderIds = planOrderApplicationService.queryOverduePlanOrderIds();
                if (CollectionUtils.isNotEmpty(overdueOrderIds)) {
                    log.info("PlanOrderOverdueCheckJob found {} overdue orders", overdueOrderIds.size());
                    planOrderApplicationService.changeOverduePlanOrderStatus(overdueOrderIds);
                }
                return null;
            });
            log.info("PlanOrderOverdueCheckJob success");
        } catch (Exception e) {
            log.error("PlanOrderOverdueCheckJob error", e);
        }
    }
}
