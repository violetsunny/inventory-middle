package com.inventory.middle.client.plan.plan.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 创建详情
 * @date 2021/9/26 14:10
 */
@Data
public class PlanMaterialParamBatchCreateDetailDTO extends PlanMaterialParameterDTO implements Serializable {

    private static final long serialVersionUID = 3959883985723129521L;
    /**
     * 创建详情
     */
    private String createMessage;
}
