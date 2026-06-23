package com.inventory.middle.interfaces.task;

import com.inventory.middle.application.service.InventoryMonitorRuleApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;

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
            inventoryMonitorRuleApplicationService.processAnnualInspection();
        } catch (Exception e) {
            log.error("MonitorAnnualInspectionJob.execute error", e);
        } finally {
            stopWatch.stop();
            log.info("MonitorAnnualInspectionJob.execute end, elapsed={}ms", stopWatch.getTotalTimeMillis());
        }
    }
}
