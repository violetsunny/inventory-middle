package com.inventory.middle.application.plan.calculate.support.formula;

import com.inventory.middle.application.plan.calculate.support.formula.factor.MaterialFactor;
import com.inventory.middle.application.plan.calculate.support.formula.indicator.Indicator;
import com.google.common.collect.Maps;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.MapUtils;

import java.time.LocalDate;
import java.util.Map;

/**
 * 公式计算线程上下文
 * <pre>
 *     使用上下文缓存计算过程中的指标值，避免重复计算
 * </pre>
 *
 * @author Danny.Lee
 * @date 2021/10/13
 */
public class FormulaContext {

    private static final ThreadLocal<FormulaContext> CONTEXT = new ThreadLocal<>();

    /**
     * 指标数据
     */
    private Map<FactorSpec, FactorIndicators> indicators;

    /**
     * 添加指标
     *
     * @param factor        物料因子
     * @param date          计划日期
     * @param indicatorCode 指标编码
     * @param value         指标数值
     */
    public void addIndicator(MaterialFactor factor, LocalDate date, String indicatorCode, Indicator value) {
        FactorSpec spec = FactorSpec.of(factor);
        if (null == indicators) {
            indicators = Maps.newHashMap();
        }
        FactorIndicators factorIndicators = indicators.computeIfAbsent(spec, s -> new FactorIndicators());
        factorIndicators.addIndicator(date, indicatorCode, value);
    }

    /**
     * 查询指标
     *
     * @param factor        物料因子
     * @param date          计划日期
     * @param indicatorCode 指标编码
     */
    public Indicator getIndicator(MaterialFactor factor, LocalDate date, String indicatorCode) {
        FactorSpec spec = FactorSpec.of(factor);
        if (null == indicators || null == indicators.get(spec) || MapUtils.isEmpty(indicators.get(spec).indicators)) {
            return null;
        }
        return indicators.get(spec).getIndicator(date, indicatorCode);
    }

    /**
     * 设置线程上下文
     */
    public static void init() {
        CONTEXT.set(new FormulaContext());
    }

    /**
     * 清理线程上下文
     */
    public static void clear() {
        CONTEXT.remove();
    }

    public static void clear(MaterialFactor factor) {
        FormulaContext context = get();
        if (null != context && MapUtils.isNotEmpty(context.indicators)) {
            context.indicators.remove(FactorSpec.of(factor));
        }
    }

    /**
     * 获取上下文
     *
     * @return 公式计算上下文
     */
    public static FormulaContext get() {
        return CONTEXT.get();
    }


    /**
     * 因子定义
     */
    @Data
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    private static final class FactorSpec {
        /**
         * 物料编码
         */
        private String materialCode;

        /**
         * 逻辑仓编码
         */
        private String logicalPlantNo;

        /**
         * 租户id
         */
        private String tenantId;

        public static FactorSpec of(MaterialFactor factor) {
            FactorSpec spec = new FactorSpec();
            spec.setMaterialCode(factor.getMaterialCode());
            spec.setLogicalPlantNo(factor.getLogicalPlantNo());
            spec.setTenantId(factor.getTenantId());
            return spec;
        }
    }

    /**
     * 因子指标
     */
    private static final class FactorIndicators {
        /**
         * 指标数据
         */
        private Map<LocalDate, Map<String, Indicator>> indicators;

        public void addIndicator(LocalDate date, String indicatorCode, Indicator value) {
            if (null == indicators) {
                indicators = Maps.newHashMap();
            }
            Map<String, Indicator> indicatorValues = indicators.computeIfAbsent(date, k -> Maps.newHashMap());
            indicatorValues.put(indicatorCode, value);
        }

        public Indicator getIndicator(LocalDate date, String indicatorCode) {
            if (null == indicators || MapUtils.isEmpty(indicators.get(date))) {
                return null;
            }
            return indicators.get(date).get(indicatorCode);
        }
    }

}
