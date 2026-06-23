package com.inventory.middle.client.plan.config.dto;

import com.inventory.middle.client.plan.BaseDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 计划订单DTO
 * @date 2021/10/19 14:09
 */
@Data
public class PlanOrderDTO extends BaseDTO implements Serializable {

    private static final long serialVersionUID = 2131725197866292590L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 计划方案号
     */
    private String planCode;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 物料描述
     */
    private String materialDesc;

    /**
     * 外部物料编码
     */
    private String externalMaterialCode;

    /**
     * 方案类型（0：采购，1：调拨，2：生产）
     */
    private Integer planType;

    /**
     * 创建类型（0：系统，1：人工）
     */
    private Integer createType;

    /**
     * 逻辑仓编码
     */
    private String logicalPlantNo;

    /**
     * 逻辑仓名称
     */
    private String logicalPlantName;

    /**
     * 预测库存
     */
    private Integer forecastInventory;

    /**
     * 计划订单量
     */
    private Integer planOrderQuantity;

    /**
     * 库存单位
     */
    private String unit;

    /**
     * 下发订单量
     */
    private Integer issueQuantity;

    /**
     * 确认订单量
     */
    private Integer confirmQuantity;

    /**
     * 完成订单量
     */
    private Integer finishQuantity;

    /**
     * 状态（0：已创建，1：部分下发，2：全部下发，3：已完结，4：已逾期）
     */
    private Integer status;

    /**
     * 计划下发日期
     */
    private Date planIssueTime;

    /**
     * 实际下发日期
     */
    private Date realIssueTime;

    /**
     * 计划收货日期
     */
    private Date planReceivingTime;

    /**
     * 实际收货日期
     */
    private Date realReceivingTime;

    /**
     * 确认日期
     */
    private Date confirmTime;

    /**
     * 计划员名称
     */
    private String plannerName;

    /**
     * 计划员ID
     */
    private String plannerId;

    /**
     * 是否已删除（0:未删除，1:已删除，默认为0）
     */
    private Integer isDelete;

    /**
     * 创建时间（默认当前时间）
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String createUserId;

    /**
     * 更新时间（默认当前时间）
     */
    private Date updateTime;

    /**
     * 更新人
     */
    private String updateUserId;

    /**
     * 操作人姓名
     */
    private String operatorName;

    /**
     * 租户Id
     */
    private String tenantId;

    /**
     * 需求信息 （json格式）
     */
    private String demandInfo;

    private Integer deleted;

    private String creatorId;

    private String updatorId;
}
