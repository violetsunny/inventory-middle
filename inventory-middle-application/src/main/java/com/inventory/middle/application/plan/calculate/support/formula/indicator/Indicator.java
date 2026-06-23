package com.inventory.middle.application.plan.calculate.support.formula.indicator;

import com.alibaba.fastjson.JSONObject;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * 指标定义
 *
 * @author Danny.Lee
 * @date 2021/10/26
 */
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Indicator {

    /**
     * 指标值
     */
    private BigDecimal value;

    /**
     * 过程数据
     */
    private JSONObject processExtAttrs;

    public static Indicator of() {
        return new Indicator();
    }

    public static Indicator of(BigDecimal value) {
        return of(value, new JSONObject());
    }

    public static Indicator of(BigDecimal value, JSONObject extAttrs) {
        Indicator spec = new Indicator();
        spec.value = value;
        spec.processExtAttrs = extAttrs;
        return spec;
    }

    public BigDecimal value() {
        return Optional.ofNullable(value).orElse(BigDecimal.ZERO);
    }

    public JSONObject extAttrs() {
        if (null == processExtAttrs) {
            processExtAttrs = new JSONObject();
        }
        return processExtAttrs;
    }

    public Indicator value(BigDecimal value) {
        // 去除小数点位
        this.value = new BigDecimal(value.stripTrailingZeros().toPlainString());
        return this;
    }

    public Indicator addExtAttr(String code, Object value) {
        if (null == processExtAttrs) {
            processExtAttrs = new JSONObject();
        }
        processExtAttrs.put(code, value);
        return this;
    }

    public Indicator addAllExtAttr(JSONObject extAttrs) {
        if (null == processExtAttrs) {
            processExtAttrs = new JSONObject();
        }
        processExtAttrs.putAll(extAttrs);
        return this;
    }

    public Indicator extAttrs(JSONObject extAttrs) {
        this.processExtAttrs = extAttrs;
        return this;
    }
}
