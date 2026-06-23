package com.inventory.middle.application.plan.plan.calculate.support.report.collector;

import top.kdla.framework.validator.BaseAssert;
import com.inventory.middle.domain.plan.common.ex.Checker;
import com.google.common.collect.Range;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Objects;

/**
 * 报表时段聚合项
 *
 * @author Danny.Lee
 * @date 2021/10/18
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CollectRange implements Comparable<CollectRange> {

    @Getter
    private Range<LocalDate> range;

    public static CollectRange of(LocalDate startDate, LocalDate endDate) {
        BaseAssert.notNull(startDate);
        BaseAssert.notNull(endDate);
        BaseAssert.isTrue(!startDate.isAfter(endDate));

        CollectRange context = new CollectRange();
        context.range = Range.closed(startDate, endDate);
        return context;
    }


    public boolean inRange(LocalDate localDate) {
        return range.contains(localDate);
    }

    public boolean isStartDate(LocalDate localDate) {
        return range.lowerEndpoint().equals(localDate);
    }

    public boolean isEndDate(LocalDate localDate) {
        return range.upperEndpoint().equals(localDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CollectRange that = (CollectRange) o;
        return Objects.equals(this.getRange().lowerEndpoint(), that.getRange().lowerEndpoint());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getRange().lowerEndpoint());
    }

    @Override
    public int compareTo(CollectRange o) {
        if (o == null) {
            return -1;
        }
        LocalDate startDate = this.getRange().lowerEndpoint();
        LocalDate compareStartDate = o.getRange().lowerEndpoint();
        return startDate.compareTo(compareStartDate);
    }
}
