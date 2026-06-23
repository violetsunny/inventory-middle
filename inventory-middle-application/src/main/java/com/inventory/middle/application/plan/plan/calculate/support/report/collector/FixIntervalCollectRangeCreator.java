package com.inventory.middle.application.plan.plan.calculate.support.report.collector;

import top.kdla.framework.validator.BaseAssert;
import com.inventory.middle.domain.plan.common.ex.Checker;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * 固定周期-报表时段聚合创建器
 *
 * @author Danny.Lee
 * @date 2021/11/8
 */
public class FixIntervalCollectRangeCreator implements CollectRangeCreator {

    private final int interval;

    public FixIntervalCollectRangeCreator(int interval) {
        this.interval = interval;
    }

    @Override
    public CollectRange range(LocalDate date, LocalDate prime) {
        BaseAssert.isTrue(!date.isBefore(prime));
        int val = (int) prime.until(date, ChronoUnit.DAYS) % interval;
        return CollectRange.of(date.minusDays(val), date.plusDays(interval - val - 1));
    }
}
