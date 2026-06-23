package com.inventory.middle.domain.plan.order.bo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.inventory.middle.domain.plan.common.bo.BaseBo;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 创建系统计划订单BO
 * <p>
 * 迁移自 com.enn.plan.management.core.plan.order.bo.SystemPlanOrderCreateBO
 * </p>
 *
 * @author migrated from scm-plan-management
 */
@Data
public class SystemPlanOrderCreateBO extends BaseBo implements Serializable {

    /**
     * MRP 计划ID
     */
    private Long planId;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 逻辑仓编码
     */
    private String logicalPlantNo;

    /**
     * 调拨逻辑仓
     */
    private String transferLogicalPlantNo;

    /**
     * 计划订单量
     */
    private Integer planOrderQuantity;

    /**
     * 预测库存
     */
    private Integer forecastInventory;

    /**
     * 计划下发日期
     */
    private Date planIssueTime;

    /**
     * 计划收货日期
     */
    private Date planReceivingTime;

    /**
     * 需求信息
     */
    private JSONArray demandInfo;

    /**
     * 扩展信息
     */
    private JSONObject extAttrs;

    @Override
    public String toLog() {
        return this.toString();
    }
}
