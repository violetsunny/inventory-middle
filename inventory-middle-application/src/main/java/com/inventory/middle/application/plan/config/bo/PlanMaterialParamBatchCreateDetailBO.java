package com.inventory.middle.application.plan.config.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 创建详情
 * @date 2021/9/26 14:10
 */
@Data
public class PlanMaterialParamBatchCreateDetailBO extends PlanMaterialParameterBO implements Serializable {

    private static final long serialVersionUID = 1133715763968968293L;
    /**
     * 创建详情
     */
    private String createMessage;
}