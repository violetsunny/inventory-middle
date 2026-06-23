package com.inventory.middle.application.plan.calculate.support.formula;

import top.kdla.framework.validator.BaseAssert;
import com.inventory.middle.domain.plan.common.ex.Checker;
import com.inventory.middle.application.plan.calculate.support.formula.factor.MaterialFactor;
import com.inventory.middle.application.plan.calculate.support.formula.indicator.Indicator;
import com.inventory.middle.application.plan.calculate.support.formula.indicator.IndicatorSpec;
import com.inventory.middle.application.plan.calculate.support.formula.indicator.Indicators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 毛需求/本期计划出
 * <pre>
 *     https://rih0iry1v9.feishu.cn/docs/doccn6m3NI85cAs77YUWqQ0V0je
 * </pre>
 * 毛需求计算依赖需求响应策略
 * <pre>
 *     1. 当需求响应策略来源=产品预测，取预测需求
 *     2. 当需求响应策略来源=订单 & 销售订单(业务上订单≠销售订单，但在本期范围内一致)时，需订单需求
 *     2. 当需求响应策略为订单+预测，冲销策略为根据冲销结果时，参考{@link Reverse}
 *     2. 当需求响应策略为订单+预测，冲销策略为根据时区时，参考{@link TimeZone}
 *     3. 当需求响应策略为订单+预测，冲销策略为根据较大值时，参考{@link Max}
 * </pre>
 *
 * @author Danny.Lee
 * @date 2021/9/26
 */
@Component
@IndicatorSpec(Indicators.GROSS_REQUIREMENT)
public class GrossRequirementFormula implements Formula {

    @Resource
    private Formula correlatedFormula;

    private static final String MTS10 = "MTS10";
    private static final String MTS11 = "MTS11";
    private static final String MTS12 = "MTS12";
    private static final String MTS13 = "MTS13";
    private static final String MTO30 = "MTO30";
    private static final String MTP = "MTP";

    @Override
    public Indicator apply(MaterialFactor materialFactor) {
        Indicator indicator = Indicator.of();
        if (materialFactor.enableBom()) {
            // Bom子件毛需求=相关需求
            BigDecimal correlated = correlatedFormula.apply(materialFactor).value();
            indicator.addExtAttr(correlatedFormula.indicatorCode(), correlated);
            return indicator.value(correlated);
        }
        // 获取需求来源
        DemandSource demandSource = this.decideDemandSource(materialFactor.getDemandStrategyCode());
        BaseAssert.notNull(demandSource);
        indicator.addExtAttr("demandSource", demandSource.name());
        if (!DemandSource.isVariousSource(demandSource)) {
            if (demandSource == DemandSource.PREDICT) {
                // 预测来源
                return indicator.value(materialFactor.predictDemand());
            } else {
                // 本期订单来源 ==  销售订单来源
                return indicator.value(materialFactor.orderDemand());
            }
        }
        Formula variousFormula = variousSourceGrossFormula(demandSource);
        BaseAssert.notNull(variousFormula);
        Indicator reverse = variousFormula.apply(materialFactor);
        return indicator.value(reverse.value()).addAllExtAttr(reverse.extAttrs());
    }

    /**
     * 获取需求来源
     * <pre>
     *     https://rih0iry1v9.feishu.cn/docs/doccn6m3NI85cAs77YUWqQ0V0je#fAkOCn
     * </pre>
     *
     * @param demandStrategyCode 需求响应策略
     * @return 需求来源
     */
    private DemandSource decideDemandSource(String demandStrategyCode) {
        if (StringUtils.equals(MTS10, demandStrategyCode)) {
            return DemandSource.PREDICT;
        }
        if (StringUtils.equals(MTS11, demandStrategyCode)) {
            return DemandSource.MAX;
        }
        if (StringUtils.equals(MTS12, demandStrategyCode)) {
            return DemandSource.REVERSE;
        }
        if (StringUtils.equals(MTS13, demandStrategyCode)) {
            return DemandSource.TIME_ZONE;
        }
        if (StringUtils.equals(MTO30, demandStrategyCode)) {
            return DemandSource.SALES;
        }
        if (StringUtils.equals(MTP, demandStrategyCode)) {
            return DemandSource.MAX;
        }
        return null;
    }

    /**
     * 获取多需求来源的毛需求计算公式
     *
     * @param demandSource 需求来源
     * @return 多需求来源的毛需求计算公式
     */
    private Formula variousSourceGrossFormula(DemandSource demandSource) {
        if (demandSource == DemandSource.REVERSE) {
            return Reverse.instance();
        }
        if (demandSource == DemandSource.TIME_ZONE) {
            return TimeZone.instance();
        }
        if (demandSource == DemandSource.MAX) {
            return Max.instance();
        }
        return null;
    }

    /**
     * 根据冲销结果
     */
    private static class Reverse implements Formula {

        private static final Reverse INSTANCE = new Reverse();

        public static Reverse instance() {
            return INSTANCE;
        }

        @Override
        public Indicator apply(MaterialFactor materialFactor) {
            Indicator indicator = Indicator.of().addExtAttr("demandSource", DemandSource.REVERSE.name());
            // 计划开始日期
            final LocalDate planStartDate = materialFactor.getPlanStartDate();
            final LocalDate planEndDate = materialFactor.getPlanEndDate();

            Map<LocalDate, BigDecimal> reverseBalances = Maps.newHashMap();
            BigDecimal totalBalance = BigDecimal.ZERO;
            for (LocalDate planDate = planStartDate;
                 !planDate.isAfter(planEndDate);
                 planDate = planDate.plusDays(1)) {

                BigDecimal orderDemand = materialFactor.orderDemand(planDate);
                BigDecimal predictDemand = materialFactor.predictDemand(planDate);

                // 计划日期的订单需求大于预测需求，返回预测值
                if (planDate.isEqual(materialFactor.getPlanDate()) && orderDemand.compareTo(predictDemand) >= 0) {
                    return indicator.value(predictDemand);
                }

                final BigDecimal reverseBalance = orderDemand.subtract(predictDemand);
                totalBalance = totalBalance.add(reverseBalance);
                reverseBalances.put(planDate, reverseBalance);
            }

            // 总额满足说明全额足够冲销，返回需求预测
            if (totalBalance.compareTo(BigDecimal.ZERO) >= 0) {
                return indicator.value(materialFactor.predictDemand(materialFactor.getPlanDate()));
            }

            // 总额不满足说明总体需求未能满足，倒序计算未冲销完的数据
            Map<LocalDate, BigDecimal> uncompletedReverseBalances = Maps.newHashMap();
            for (LocalDate planDate = planEndDate;
                 totalBalance.compareTo(BigDecimal.ZERO) < 0 && !planDate.isBefore(planStartDate);
                 planDate = planDate.minusDays(1)) {
                BigDecimal reverseBalance = reverseBalances.get(planDate);
                if (reverseBalance.compareTo(BigDecimal.ZERO) < 0) {
                    if (reverseBalance.compareTo(totalBalance) <= 0) {
                        uncompletedReverseBalances.put(planDate, totalBalance);
                    } else {
                        uncompletedReverseBalances.put(planDate, reverseBalance);
                    }
                    totalBalance = totalBalance.subtract(reverseBalance);
                }
            }
            // 未冲销完记录，计算冲销余额
            if (uncompletedReverseBalances.containsKey(materialFactor.getPlanDate())) {
                return indicator.value(materialFactor.predictDemand().add(uncompletedReverseBalances.get(materialFactor.getPlanDate())));
            }
            // 不在未冲销完记录中，表示此前已冲销完成，返回预测值
            return indicator.value(materialFactor.predictDemand());
        }
    }

    /**
     * 根据时区
     */
    private static class TimeZone implements Formula {

        private static final TimeZone INSTANCE = new TimeZone();

        public static TimeZone instance() {
            return INSTANCE;
        }

        @Override
        public Indicator apply(MaterialFactor materialFactor) {
            Indicator indicator = Indicator.of().addExtAttr("demandSource", DemandSource.TIME_ZONE.name());
            // 计划开始日期
            final LocalDate planStartDate = materialFactor.getPlanStartDate();
            final LocalDate planDate = materialFactor.getPlanDate();
            // 需求时区临界日期
            int demandFence = Optional.ofNullable(materialFactor.getDemandTimeFence()).orElse(0);
            final LocalDate demandTimeZoneDate = planStartDate.plusDays(demandFence);
            // 计划时区临界日期
            int planFence = Optional.ofNullable(materialFactor.getPlanningTimeFence()).orElse(0);
            final LocalDate planTimeZoneDate = demandTimeZoneDate.plusDays(planFence);

            // 临界条件=>左闭右开
            if (planDate.isBefore(demandTimeZoneDate)) {
                // 需求时区
                return indicator.value(materialFactor.orderDemand());
            } else if (planDate.isBefore(planTimeZoneDate)) {
                // 计划时区
                return indicator.value(Max.instance().apply(materialFactor).value());
            } else {
                // 预测时区
                return indicator.value(materialFactor.predictDemand());
            }
        }
    }

    /**
     * 根据较大值
     */
    private static class Max implements Formula {

        private static final Max INSTANCE = new Max();

        public static Max instance() {
            return INSTANCE;
        }

        @Override
        public Indicator apply(MaterialFactor materialFactor) {
            Indicator indicator = Indicator.of().addExtAttr("demandSource", DemandSource.MAX.name());
            BigDecimal orderDemand = materialFactor.orderDemand();
            BigDecimal predictDemand = materialFactor.predictDemand();
            return indicator.value(orderDemand.compareTo(predictDemand) > 0 ? orderDemand : predictDemand);
        }
    }

    /**
     * 需求来源
     */
    private enum DemandSource {
        /**
         * 产品预测
         */
        PREDICT,
        /**
         * 订单
         */
        ORDER,
        /**
         * 销售订单
         */
        SALES,
        /**
         * 订单 & 预测 => 根据冲销结果
         */
        REVERSE,
        /**
         * 订单 & 预测 => 根据时区
         */
        TIME_ZONE,
        /**
         * 订单 & 预测 => 根据较大值
         */
        MAX;

        static final List<DemandSource> VARIOUS_SOURCES = Lists.newArrayList(REVERSE, TIME_ZONE, MAX);

        public static boolean isVariousSource(DemandSource source) {
            return VARIOUS_SOURCES.contains(source);
        }

    }
}
