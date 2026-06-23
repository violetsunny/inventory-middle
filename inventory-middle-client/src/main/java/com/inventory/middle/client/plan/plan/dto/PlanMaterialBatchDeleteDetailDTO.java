package com.inventory.middle.client.plan.plan.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 批量删除详情
 * @date 2021/11/9 11:12
 */
@Data
public class PlanMaterialBatchDeleteDetailDTO extends PlanMaterialDTO implements Serializable {

    private static final long serialVersionUID = 1133715763968968293L;
    /**
     * 删除详情
     */
    private String deleteMessage;
}
