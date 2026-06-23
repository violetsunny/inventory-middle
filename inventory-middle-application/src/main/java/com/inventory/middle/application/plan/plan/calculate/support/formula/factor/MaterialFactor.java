package com.inventory.middle.application.plan.plan.calculate.support.formula.factor;

import lombok.Data;
import lombok.experimental.Delegate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

/**
 * 物料因子
 *
 * @author Danny.Lee
 * @date 2021/9/26
 */
@Data
public class MaterialFactor implements Factor {

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 逻辑仓
     */
    private String logicalPlantNo;

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 物料计划因子
     */
    @Delegate
    private ParameterFactor parameterFactor;

    /**
     * 需求因子
     */
    @Delegate
    private DemandFactor demandFactor;

    /**
     * Bom因子
     */
    private BomFactor bomFactor;

    /**
     * 当前库存
     */
    private BigDecimal currentStock;

    /**
     * 计划日期
     */
    private LocalDate planDate;

    /**
     * 需求开始时间
     */
    private LocalDate planStartDate;

    /**
     * 需求结束时间
     */
    private LocalDate planEndDate;

    public BigDecimal orderDemand() {
        return this.orderDemand(this.planDate);
    }

    public BigDecimal predictDemand() {
        return this.predictDemand(this.planDate);
    }

    public boolean enableBom(){
        return Objects.nonNull(bomFactor);
    }

    public BigDecimal correlatedPlanProduce() {
        return Optional.ofNullable(bomFactor)
                .map(factorOfBom -> factorOfBom.correlatedPlanProduce(this.planDate))
                .orElse(BigDecimal.ZERO);
    }

    public BigDecimal correlatedPlanInvest() {
        return Optional.ofNullable(bomFactor)
                .map(factorOfBom -> factorOfBom.correlatedPlanInvest(this.planDate))
                .orElse(BigDecimal.ZERO);
    }

    public boolean isStartDate() {
        return planDate.isEqual(planStartDate);
    }

    public boolean isBeforeStartDate() {
        return planDate.isBefore(planStartDate);
    }


    public MaterialFactor previous() {
        MaterialFactor materialFactor = this.copy();
        materialFactor.setPlanDate(materialFactor.getPlanDate().minusDays(1));
        return materialFactor;
    }

    public MaterialFactor next() {
        return nextDays(1);
    }

    public MaterialFactor nextDays(int days) {
        MaterialFactor materialFactor = this.copy();
        materialFactor.setPlanDate(materialFactor.getPlanDate().plusDays(days));
        return materialFactor;
    }

    public MaterialFactor now() {
        MaterialFactor factor = this.copy();
        factor.setPlanDate(LocalDate.now());
        return factor;
    }

    public MaterialFactor copy() {
        MaterialFactor materialFactor = new MaterialFactor();
        materialFactor.setTenantId(this.tenantId);
        materialFactor.setMaterialCode(this.materialCode);
        materialFactor.setLogicalPlantNo(this.logicalPlantNo);
        materialFactor.setCurrentStock(this.currentStock);

        materialFactor.setPlanStartDate(this.planStartDate);
        materialFactor.setPlanEndDate(this.planEndDate);
        materialFactor.setPlanDate(this.planDate);

        materialFactor.setBomFactor(this.bomFactor);
        materialFactor.setDemandFactor(this.demandFactor);
        materialFactor.setParameterFactor(this.parameterFactor);

        return materialFactor;
    }

}
