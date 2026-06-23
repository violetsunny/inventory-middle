package com.inventory.middle.client.plan.config.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 计划执行结果
 *
 * @author Danny.Lee
 * @date 2021/9/29
 */
@Data
public class PlanInstanceDTO implements Serializable {

    private static final long serialVersionUID = -7243902849689674063L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 方案id
     */
    private Long planId;

    /**
     * 方案编码
     */
    private String planCode;

    /**
     * 计划版本号
     */
    private String planVersion;

    /**
     * 方案类型
     */
    private Integer planType;

    /**
     * 租户id
     */
    private String tenantId;

}
