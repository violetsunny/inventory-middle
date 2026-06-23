package com.inventory.middle.application.plan.demand.bo;

import lombok.Data;
import com.inventory.middle.domain.plan.common.bo.BaseBo;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 创建详情
 * @date 2021/9/26 14:10
 */
@Data
public class DemandPlanMaterialBatchCreateDetailBO extends BaseBo {

    private static final long serialVersionUID = 3959883985723129521L;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 返回消息
     */
    private String message;

    @Override
    public String toLog() {
        return this.toString();
    }
}