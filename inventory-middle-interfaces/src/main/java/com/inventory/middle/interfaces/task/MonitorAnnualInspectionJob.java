package com.inventory.middle.interfaces.task;

import com.inventory.middle.application.service.InventoryMonitorRuleApplicationService;
import com.inventory.middle.infra.lock.RedissonDistributedLockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 库存中心 物料年检预警定时任务
 * <p>
 * 迁移自: com.enn.inventory.center.processor.job.MonitorAnnualInspectionJob
 * <p>
 * 原来使用 RDFA 的 @RdfaJob("InventoryAnnualInspectionMonitorJob") + RdfaJobHandler
 * 迁移后改用 Spring @Scheduled，如需接入 XXL-JOB 等调度平台，替换 @Scheduled 注解即可。
 */
@Slf4j
@Component
public class MonitorAnnualInspectionJob {

    @Resource
    private InventoryMonitorRuleApplicationService inventoryMonitorRuleApplicationService;

    @Resource
    private RedissonDistributedLockService redissonDistributedLockService;

    private static final String LOCK_KEY = "JOB:MONITOR_ANNUAL_INSPECTION";

    /**
     * 年检预警计算任务
     * 默认每天凌晨 2 点执行，可通过配置项覆盖
     */
    @Scheduled(cron = "${inventory.task.annual-inspection.cron:0 0 2 * * ?}")
    public void execute() {
        StopWatch stopWatch = new StopWatch("MonitorAnnualInspectionJob");
        stopWatch.start();
        log.info("MonitorAnnualInspectionJob.execute start");
        try {
            redissonDistributedLockService.executeWithLock(100L, TimeUnit.MILLISECONDS, LOCK_KEY, () -> {
                inventoryMonitorRuleApplicationService.processAnnualInspection();
                return null;
            });
        } catch (Exception e) {
            log.error("MonitorAnnualInspectionJob.execute error", e);
        } finally {
            stopWatch.stop();
            log.info("MonitorAnnualInspectionJob.execute end, elapsed={}ms", stopWatch.getTotalTimeMillis());
        }
    }
}
