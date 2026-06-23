package com.inventory.middle.application.plan.plan.config.bo;

import com.inventory.middle.infra.plan.persistence.condition.plan.PageCondition;
import lombok.Data;

import java.io.Serializable;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 分页查询计划方案入参
 * @date 2021/10/1 14:49
 */
@Data
public class PlanPageReqBO extends PageCondition implements Serializable {

    private static final long serialVersionUID = -1132999544908961180L;

    /**
     * 计划方案号
     */
    private String planCode;

    /**
     * 计划描述
     */
    private String planDesc;

    /**
     * 执行类型
     */
    private Integer planType;

    /**
     * 操作人
     */
    private String operatorName;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 状态
     */
    private Integer status;
}