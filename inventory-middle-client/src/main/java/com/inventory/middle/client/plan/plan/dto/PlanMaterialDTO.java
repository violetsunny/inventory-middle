package com.inventory.middle.client.plan.plan.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 计划方案物料
 * @date 2021/10/2 11:03
 */
@Data
public class PlanMaterialDTO implements Serializable {

    private static final long serialVersionUID = 8560277045601709248L;


    /**
     * 主键
     */
    private Long id;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 逻辑仓编码
     */
    private String logicalPlantNo;

    /**
     * 计划物料使用场景
     */
    private Integer type;

    /**
     * 计划方案Id
     */
    private Long sourceId;

    /**
     * 创建时间（默认当前时间）
     */
    private Date createTime;

    /**
     * 创建时间字符串格式
     */
    private String createTimeStr;

    /**
     * 更新时间（默认当前时间）
     */
    private Date updateTime;

    /**
     * 更新时间字符串格式
     */
    private String updateTimeStr;

    /**
     * 操作人Id
     */
    private String operatorId;

    /**
     * 租户Id
     */
    private String tenantId;

    /**
     * 表格index
     */
    private Integer index;
}
