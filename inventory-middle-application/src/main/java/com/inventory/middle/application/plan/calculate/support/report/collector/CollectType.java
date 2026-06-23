package com.inventory.middle.application.plan.calculate.support.report.collector;


import top.kdla.framework.validator.BaseAssert;
import com.inventory.middle.domain.plan.common.ex.Checker;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.Objects;

/**
 * 计划报表时段聚合器
 *
 * @author Danny.Lee
 * @date 2021/10/18
 */
public enum CollectType implements CollectRangeCreator{

    /**
     * 按日聚合
     */
    DATE(0, "日期") {
        @Override
        public CollectRange range(LocalDate date, LocalDate prime) {
            BaseAssert.isTrue(!date.isBefore(prime));
            return CollectRange.of(date, date);
        }
    },
    /**
     * 按周(7日)聚合
     */
    WEEK(1, "周期-7天") {
        @Override
        public CollectRange range(LocalDate date, LocalDate prime) {
            BaseAssert.isTrue(!date.isBefore(prime));
            int val = (int) prime.until(date, ChronoUnit.DAYS) % 7;
            return CollectRange.of(date.minusDays(val), date.plusDays(6 - val));
        }
    },
    /**
     * 按自然周聚合
     */
    NATURAL_WEEK(2, "自然周") {
        @Override
        public CollectRange range(LocalDate date, LocalDate prime) {
            BaseAssert.isTrue(!date.isBefore(prime));
            LocalDate startDate = date.with(DayOfWeek.MONDAY).isBefore(prime) ? prime : date.with(DayOfWeek.MONDAY);
            return CollectRange.of(startDate, date.with(DayOfWeek.SUNDAY));
        }
    },
    /**
     * 按月(30日)聚合
     */
    MONTH(3, "月度-30天") {
        @Override
        public CollectRange range(LocalDate date, LocalDate prime) {
            BaseAssert.isTrue(!date.isBefore(prime));
            int val = (int) prime.until(date, ChronoUnit.DAYS) % 30;
            return CollectRange.of(date.minusDays(val), date.plusDays(29 - val));
        }
    },
    /**
     * 按自然月聚合
     */
    NATURAL_MONTH(4, "自然月") {
        @Override
        public CollectRange range(LocalDate date, LocalDate prime) {
            BaseAssert.isTrue(!date.isBefore(prime));
            return CollectRange.of(date.with(TemporalAdjusters.firstDayOfMonth()), date.with(TemporalAdjusters.lastDayOfMonth()));
        }
    },
    ;

    private final Integer code;

    private final String desc;

    CollectType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static CollectType of(Integer collectType) {
        return Arrays.stream(CollectType.values())
                .filter(collect -> Objects.equals(collectType, collect.code))
                .findFirst().orElse(null);
    }
}
