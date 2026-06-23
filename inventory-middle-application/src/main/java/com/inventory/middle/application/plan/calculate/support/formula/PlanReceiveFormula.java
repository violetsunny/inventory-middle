package com.inventory.middle.application.plan.calculate.support.formula;

import com.inventory.middle.domain.plan.common.enums.PlanOrderStatusEnum;
import com.inventory.middle.infra.plan.util.DateUtils;
import com.inventory.middle.application.plan.calculate.support.formula.factor.MaterialFactor;
import com.inventory.middle.application.plan.calculate.support.formula.indicator.Indicator;
import com.inventory.middle.application.plan.calculate.support.formula.indicator.IndicatorSpec;
import com.inventory.middle.application.plan.calculate.support.formula.indicator.Indicators;
import com.inventory.middle.infra.plan.persistence.dao.PlanOrderDao;
import com.inventory.middle.infra.plan.persistence.condition.PlanOrderCondition;
import com.inventory.middle.infra.plan.persistence.entity.PlanOrderPO;
import com.inventory.middle.client.plan.dto.inventory.request.MaterialPlanInventoryRequest;
import com.inventory.middle.application.plan.support.InventorySupportService;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * 本期计划接收量<br/>
 * 本期计划接收量 = 本期计划入库 + 逾期本期入库(计划日期=当前日期时需要考虑) + 本期计划订单确认后的计划产出量+逾期计划订单确认后的计划产出量
 *
 * @author Danny.Lee
 * @date 2021/9/26
 */
@Component
@IndicatorSpec(Indicators.PLAN_RECEIVE)
public class PlanReceiveFormula implements Formula {

    @Resource
    private InventorySupportService inventorySupportService;

    @Resource
    private PlanOrderDao planOrderDao;

    @Override
    public Indicator apply(MaterialFactor materialFactor) {
        Indicator indicator = Indicator.of();
        // 构建库存查询请求
        MaterialPlanInventoryRequest request = this.buildPlanInventoryRequest(materialFactor);
        // 获取本期入库数量
        BigDecimal receiveInventory = this.querySupplyInventory(materialFactor.getPlanDate(), request);
        indicator.addExtAttr("supplyInventory", receiveInventory);

        // 获取逾期入库数量
        BigDecimal overdueReceiveInventory = BigDecimal.ZERO;
        // 当且计划日期=当前日期时需要考虑逾期数据
        if (materialFactor.isStartDate()) {
            overdueReceiveInventory = this.queryOverdueSupplyInventory(request);
        }
        indicator.addExtAttr("overdueSupplyInventory", overdueReceiveInventory);

        // 获取本期确认计划订单产出量
        BigDecimal confirmOrderAmount = this.queryConfirmOrderAmount(materialFactor);
        indicator.addExtAttr("confirmOrderAmount", confirmOrderAmount);

        // 获取本期逾期订单未完成量=产出量-完成量
        BigDecimal overdueOrderUnfinishedAmount = this.queryOverdueOrderUnfinishedAmount(materialFactor);
        indicator.addExtAttr("overdueOrderUnfinishedAmount", overdueOrderUnfinishedAmount);

        // 本期计划接收量 = 本期入库量 + 逾期入库量 + 本期确认计划订单产出量 + 本期逾期订单未完成量
        return indicator.value(receiveInventory.add(overdueReceiveInventory).add(confirmOrderAmount).add(overdueReceiveInventory));
    }

    private MaterialPlanInventoryRequest buildPlanInventoryRequest(MaterialFactor materialFactor) {
        MaterialPlanInventoryRequest request = new MaterialPlanInventoryRequest().planSupply();
        request.setMaterialCode(materialFactor.getMaterialCode());
        request.setPlantCode(materialFactor.getLogicalPlantNo());
        request.setTenant(materialFactor.getTenantId());
        request.setStartTime(materialFactor.getPlanDate());
        request.setEndTime(materialFactor.getPlanDate());
        return request;
    }

    private BigDecimal querySupplyInventory(LocalDate planDate, MaterialPlanInventoryRequest request) {
        Map<LocalDate, BigDecimal> supplyInventories = inventorySupportService.queryPlanInventory(request);
        return supplyInventories.getOrDefault(planDate, BigDecimal.ZERO);
    }

    private BigDecimal queryOverdueSupplyInventory(MaterialPlanInventoryRequest request) {
        return Optional.ofNullable(inventorySupportService.queryOverduePlanInventory(request))
                .orElse(BigDecimal.ZERO);
    }

    private BigDecimal queryConfirmOrderAmount(MaterialFactor materialFactor) {
        PlanOrderCondition condition = new PlanOrderCondition();
        condition.setTenantId(materialFactor.getTenantId());
        condition.setMaterialCode(materialFactor.getMaterialCode());
        condition.setLogicalPlantNo(materialFactor.getLogicalPlantNo());
        condition.setPlanReceivingTime(DateUtils.toDate(materialFactor.getPlanDate()));
        condition.setStatuses(Lists.newArrayList(PlanOrderStatusEnum.CONFIRMED.getCode()));

        List<PlanOrderPO> planOrders = planOrderDao.listOrdersByCondition(condition);
        return this.handlePlanOrders(planOrders, planOrder -> new BigDecimal(planOrder.getPlanOrderQuantity()));
    }

    private BigDecimal queryOverdueOrderUnfinishedAmount(MaterialFactor materialFactor) {
        PlanOrderCondition condition = new PlanOrderCondition();
        condition.setTenantId(materialFactor.getTenantId());
        condition.setMaterialCode(materialFactor.getMaterialCode());
        condition.setLogicalPlantNo(materialFactor.getLogicalPlantNo());
        condition.setPlanReceivingTime(DateUtils.toDate(materialFactor.getPlanDate()));
        condition.setStatuses(Lists.newArrayList(PlanOrderStatusEnum.ISSUE_OVERDUE.getCode(), PlanOrderStatusEnum.FINISH_OVERDUE.getCode()));

        List<PlanOrderPO> planOrders = planOrderDao.listOrdersByCondition(condition);
        return this.handlePlanOrders(planOrders, planOrder ->
                new BigDecimal(planOrder.getPlanOrderQuantity()).subtract(new BigDecimal(Optional.ofNullable(planOrder.getFinishQuantity()).orElse(0)))
        );
    }

    private BigDecimal handlePlanOrders(List<PlanOrderPO> planOrders, Function<PlanOrderPO, BigDecimal> amountFunc) {
        if (CollectionUtils.isEmpty(planOrders)) {
            return BigDecimal.ZERO;
        }
        return planOrders.stream().map(amountFunc).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

}
