package com.inventory.middle.client.plan.demand.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 创建详情
 * @date 2021/9/26 14:10
 */
@Data
public class DemandPlanMaterialBatchCreateDetailDTO implements Serializable {

    private static final long serialVersionUID = 3959883985723129521L;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 返回消息
     */
    private String message;
}
