package com.inventory.middle.application.plan.demand.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
    * 订单需求计划表
    */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDemandBO {
    /**
    * 主键
    */
    private Long id;

    /**
    * 订单号
    */
    private String orderNo;

    /**
    * 来源类型，1:项目订单
    */
    private Integer orderType;

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

    /**
    * 计划日期
    */
    private Date planDate;

    /**
    * 计划数量
    */
    private Long amount;

    /**
    * 扩展信息
    */
    private String extInfo;

    /**
    * 创建时间（默认当前时间）
    */
    private Date createTime;


}