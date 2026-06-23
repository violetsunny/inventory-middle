package com.inventory.middle.application.plan.demand.message;

import lombok.Data;

import java.util.Date;

/**
 * @description: 需求计划物料变化
 * @author:Vincent.Xiao
 * @date:2021/10/28 14:29
 */

@Data
public class DemandPlanMessage{

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 	逻辑仓编码
     */
    private String logicalPlantNo;

    /**
     * 	租户id
     */
    private String tenantId;

    /**
     * 日期
     */
    private Date planDate;

    /**
     * 数量
     */
    private Long amount;

    /**
     * 单据
     */
    private String voucherNo;

    /**
     * 行号
     */
    private String documentNo;


}
