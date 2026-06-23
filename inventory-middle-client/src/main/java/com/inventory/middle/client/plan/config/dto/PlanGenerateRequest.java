package com.inventory.middle.client.plan.config.dto;

import com.inventory.middle.client.plan.BaseDTO;
import lombok.Data;

import java.io.Serializable;

/**
 * 计划执行请求
 *
 * @author Danny.Lee
 * @date 2021/9/29
 */
@Data
public class PlanGenerateRequest extends BaseDTO implements Serializable {
    private static final long serialVersionUID = -5119268814953462803L;

    /**
     * 计划id
     */
    private Long planId;

    /**
     * 操作人
     */
    private String operator;

    /**
     * 租户id
     */
    private String tenantId;
}
