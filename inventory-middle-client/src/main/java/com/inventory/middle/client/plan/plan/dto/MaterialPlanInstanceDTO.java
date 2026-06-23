package com.inventory.middle.client.plan.plan.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 物料计划执行结果
 *
 * @author Danny.Lee
 * @date 2021/9/29
 */
@Data
public class MaterialPlanInstanceDTO implements Serializable {

    private static final long serialVersionUID = 8900491524107678614L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 实例id
     */
    private Long instanceId;

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
     * 物料层级
     */
    private String materialLevel;

    /**
     * 逻辑仓编码
     */
    private String logicalPlantNo;

    /**
     * 逻辑仓名称
     */
    private String logicalPlantName;

    /**
     * 计划类型
     */
    private Integer planType;

    /**
     * 计划方案号
     */
    private String planCode;

    /**
     * 计划版本号
     */
    private String planVersion;

    /**
     * 需求响应策略编码
     */
    private String demandStrategyCode;

    /**
     * 物料计划实例状态
     */
    private Integer status;

    /**
     * 是否删除（0-未删除/1-已删除，默认0）
     */
    private Integer isDelete;

    /**
     * 创建时间（默认当前时间）
     */
    private Date createTime;

    /**
     * 创建人（0-系统）
     */
    private String createUserId;

    /**
     * 创建人名称
     */
    private String createUserName;

    /**
     * 租户id
     */
    private String tenantId;
}
