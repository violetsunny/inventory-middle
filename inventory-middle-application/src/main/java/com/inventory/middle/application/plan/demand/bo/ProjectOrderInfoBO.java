package com.inventory.middle.application.plan.demand.bo;

import lombok.Data;

import java.util.Date;

/**
 * @author xiaokang
 */
@Data
public class ProjectOrderInfoBO {
    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 行号
     */
    private String documentNo;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 物料描述
     */
    private String materialDesc;

    /**
     * 逻辑仓编码
     */
    private String logicalPlantNo;

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 计划日期
     */
    private Date planDate;

    /**
     * 计划数量
     */
    private Long amount;

    /**
     * 合规标识 0：计划外 1：计划内
     */
    private Integer planFlag;

    /**
     * 下发时间
     */
    private Date createTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 备货计划单号
     */
    private String stockUpPlanNo;

    /**
     * 单位
     */
    private String unit;
    /**
     * 工序号
     */
    private String processNo;

    /**
     * 工序名称
     */
    private String processName;

}
