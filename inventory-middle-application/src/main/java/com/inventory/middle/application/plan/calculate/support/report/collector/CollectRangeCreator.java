package com.inventory.middle.application.plan.calculate.support.report.collector;

import java.time.LocalDate;

/**
 * Creator of {@link CollectRange}
 *
 * @author Danny.Lee
 * @date 2021/11/8
 */
public interface CollectRangeCreator {


    /**
     * 获取时间所在周期
     *
     * @param date  时间(>=初始时间)
     * @param prime 初始时间
     * @return 时间所在周期
     */
    CollectRange range(LocalDate date, LocalDate prime);
}
