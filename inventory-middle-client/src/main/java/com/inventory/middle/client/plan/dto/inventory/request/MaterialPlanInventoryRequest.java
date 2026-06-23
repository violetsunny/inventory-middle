package com.inventory.middle.client.plan.dto.inventory.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * 物料计划库存查询请求
 *
 * @author Danny.Lee (migrated from scm-plan-management)
 * @date 2022/4/27
 */
public class MaterialPlanInventoryRequest extends MaterialInventoryRequest {

    private static final long serialVersionUID = 2289041148133143728L;

    /**
     * 计划类型
     */
    private PlanType type;

    /**
     * 查询开始时间(左右闭合)
     */
    @Getter
    @Setter
    private LocalDate startTime;

    /**
     * 查询结束时间(左右闭合)
     */
    @Getter
    @Setter
    private LocalDate endTime;

    public MaterialPlanInventoryRequest planDemand() {
        this.type = PlanType.DEMAND;
        return this;
    }

    public MaterialPlanInventoryRequest planSupply() {
        this.type = PlanType.SUPPLY;
        return this;
    }

    public boolean isPlanDemand() {
        return this.hasPlanType() && this.type == PlanType.DEMAND;
    }

    public boolean isPlanSupply() {
        return this.hasPlanType() && this.type == PlanType.SUPPLY;
    }

    public boolean hasPlanType() {
        return null != type;
    }

    /**
     * 计划类型
     */
    protected enum PlanType {
        /**
         * 需求
         */
        DEMAND,
        /**
         * 供给
         */
        SUPPLY,
    }
}
