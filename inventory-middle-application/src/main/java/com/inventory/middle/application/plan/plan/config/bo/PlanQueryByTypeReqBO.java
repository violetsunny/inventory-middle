package com.inventory.middle.application.plan.plan.config.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 计划方案查询入参
 * @date 2021/10/1 14:19
 */
@Data
public class PlanQueryByTypeReqBO implements Serializable {

    private static final long serialVersionUID = 8416334399842930104L;

    /**
     * 租户Id
     */
    private String tenantId;


    /**
     * 计划方案类型
     */
    private Integer planType;

}