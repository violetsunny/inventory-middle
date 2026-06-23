package com.inventory.middle.client.plan.config.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 计划方案物料
 * @date 2021/10/2 11:03
 */
@Data
@EqualsAndHashCode
public class PlanParamDTO implements Serializable {

    private static final long serialVersionUID = 156027704514709248L;


    /**
     * 枚举编码
     */
    private String code;

    /**
     * 枚举描述
     */
    private String desc;

}
