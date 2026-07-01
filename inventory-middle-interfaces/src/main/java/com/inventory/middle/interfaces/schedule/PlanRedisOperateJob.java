package com.inventory.middle.interfaces.schedule;

import com.inventory.middle.infra.lock.RedissonDistributedLockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class PlanRedisOperateJob {

    @Resource
    private RedissonDistributedLockService redissonDistributedLockService;

    private static final String LOCK_KEY = "JOB:PLAN_REDIS_OPERATE";

    @Scheduled(cron = "${plan.job.planRedisOperate.cron:0 0 4 * * ?}")
    public void execute() {
        log.info("PlanRedisOperateJob start");
        try {
            redissonDistributedLockService.executeWithLock(100L, TimeUnit.MILLISECONDS, LOCK_KEY, () -> {
                log.info("PlanRedisOperateJob execute（Redis 操作迁移待完成，原 RDFA 参数化执行待改造）");
                return null;
            });
            log.info("PlanRedisOperateJob success");
        } catch (Exception e) {
            log.error("PlanRedisOperateJob error", e);
        }
    }
}
