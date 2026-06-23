package com.inventory.middle.application.plan.plan.config.bo;

import com.inventory.middle.application.plan.plan.config.bo.PlanMaterialBO;
import lombok.Data;

import java.io.Serializable;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 计划物料创建详情
 * @date 2021/10/2 11:09
 */
@Data
public class PlanMaterialBatchCreateDetailBO extends PlanMaterialBO implements Serializable {

    private static final long serialVersionUID = 1133715763968968293L;
    /**
     * 创建详情
     */
    private String createMessage;
}