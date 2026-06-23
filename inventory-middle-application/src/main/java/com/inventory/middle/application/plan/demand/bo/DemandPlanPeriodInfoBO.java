package com.inventory.middle.application.plan.demand.bo;

import com.inventory.middle.domain.plan.common.bo.BaseBo;
import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author:xinao
 * @date:2021/9/28 17:24
 */
@Data
public class DemandPlanPeriodInfoBO extends BaseBo {
    private static final long serialVersionUID = -6793813136446341594L;


    /**
     * 周期信息
     */
    private String planPeriod;


    /**
     * 预测需求数量
     */
    private String planAmount;

    /**
     * 扩展信息
     */
    private String extInfo;

    /**
     * 需求类型
     */
    private Integer demandType;

    @Override
    public String toLog() {
        return null;
    }
}
