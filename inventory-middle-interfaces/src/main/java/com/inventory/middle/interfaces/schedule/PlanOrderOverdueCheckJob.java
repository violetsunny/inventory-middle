package com.inventory.middle.interfaces.schedule;

import com.inventory.middle.infra.plan.persistence.dao.PlanOrderDao;
import com.inventory.middle.infra.plan.persistence.entity.PlanOrderPO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class PlanOrderOverdueCheckJob {

    @Resource
    private PlanOrderDao planOrderDao;

    @Scheduled(cron = "${plan.job.planOrderOverdueCheck.cron:0 0 3 * * ?}")
    public void execute() {
        log.info("PlanOrderOverdueCheckJob start");
        try {
            log.info("PlanOrderOverdueCheckJob success");
        } catch (Exception e) {
            log.error("PlanOrderOverdueCheckJob error", e);
        }
    }
}
