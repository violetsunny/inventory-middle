package com.inventory.middle.client.plan.plan.dto;

import com.inventory.middle.client.plan.PageRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 分页查询计划方案入参
 * @date 2021/10/1 14:49
 */
@Data
public class PlanPageReqDTO extends PageRequest implements Serializable {

    private static final long serialVersionUID = -3349832447927865191L;
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
     * 状态
     */
    private Integer status;

    /**
     * 操作人
     */
    private String operatorName;

    /**
     * 租户ID
     */
    private String tenantId;
}
