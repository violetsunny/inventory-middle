package com.inventory.middle.application.plan.plan.calculate.support.formula;

import com.inventory.middle.application.plan.plan.calculate.support.formula.factor.MaterialFactor;
import com.inventory.middle.application.plan.plan.calculate.support.formula.indicator.Indicator;
import com.inventory.middle.application.plan.plan.calculate.support.formula.indicator.IndicatorSpec;
import com.inventory.middle.application.plan.plan.calculate.support.formula.indicator.Indicators;
import com.inventory.middle.client.plan.dto.inventory.InventoryQueryRequest;
import com.inventory.middle.client.plan.dto.inventory.request.MaterialPlanInventoryRequest;
import com.inventory.middle.application.plan.support.InventorySupportService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.Optional;

/**
 * 预计可用库存量<br/>
 * <p>
 * 期初预计可用库存 = 当前库存 - 本期计划出 - 历史逾期出
 * <br/>
 * 预计可用库存量 = 计划产出量 + (上期末预计可用库存量 + 计划接收量 - 毛需求量)
 * 预计可用库存量 = 计划产出量 + 可用库存{@link AvailableInventoryFormula}
 * </p>
 *
 * @author Danny.Lee
 * @date 2021/9/26
 */
@Component
@IndicatorSpec(Indicators.PAB)
public class ProjectedAvailableBalanceFormula implements Formula {

    @Resource
    private Formula planProduceFormula;

    @Resource
    private Formula availableInventoryFormula;

    @Resource
    private InventorySupportService inventorySupportService;

    @Override
    public Indicator apply(MaterialFactor materialFactor) {
        Indicator indicator = Indicator.of();
        if (materialFactor.isBeforeStartDate()) {
            InventoryQueryRequest request = this.buildInventoryQueryRequest(materialFactor);
            // 获取本期出库数量
            BigDecimal demandInventory = this.queryDemandInventory(request);
            // 获取逾期出库数量
            BigDecimal overdueDemandInventory = this.queryOverdueDemandInventory(request);
            return indicator.value(materialFactor.getCurrentStock()
                    .subtract(demandInventory)
                    .subtract(overdueDemandInventory));
        }

        // 可用库存
        BigDecimal availableInventory = availableInventoryFormula.apply(materialFactor).value();
        indicator.addExtAttr(availableInventoryFormula.indicatorCode(), availableInventory);

        // 计划产出量
        BigDecimal planProduce = planProduceFormula.apply(materialFactor).value();
        indicator.addExtAttr(planProduceFormula.indicatorCode(), planProduce);

        // 预计可用库存量 = 计划产出量 + 可用库存
        return indicator.value(planProduce
                .add(availableInventory));
    }

    /**
     * 构建库存查询请求
     *
     * @param materialFactor 物料因子
     * @return 库存查询请求
     */
    private InventoryQueryRequest buildInventoryQueryRequest(MaterialFactor materialFactor) {
        InventoryQueryRequest request = new InventoryQueryRequest();
        request.setMaterialCode(materialFactor.getMaterialCode());
        request.setLogicalPlantNo(materialFactor.getLogicalPlantNo());
        request.setTenantId(materialFactor.getTenantId());
        request.setStartTime(materialFactor.getPlanDate().atStartOfDay());
        request.setEndTime(materialFactor.getPlanDate().atTime(LocalTime.MAX));
        return request;
    }

    private BigDecimal queryDemandInventory(InventoryQueryRequest request) {
        MaterialPlanInventoryRequest req = new MaterialPlanInventoryRequest();
        req.setMaterialCode(request.getMaterialCode());
        req.setPlantCode(request.getLogicalPlantNo());
        req.setTenant(request.getTenantId());
        req.setStartTime(request.getStartTime().toLocalDate());
        req.setEndTime(request.getEndTime().toLocalDate());
        req.planDemand();
        Map<LocalDate, BigDecimal> demandInventories = inventorySupportService.queryPlanInventory(req);
        return demandInventories.getOrDefault(request.getStartTime().toLocalDate(), BigDecimal.ZERO);
    }

    private BigDecimal queryOverdueDemandInventory(InventoryQueryRequest request) {
        MaterialPlanInventoryRequest req = new MaterialPlanInventoryRequest();
        req.setMaterialCode(request.getMaterialCode());
        req.setPlantCode(request.getLogicalPlantNo());
        req.setTenant(request.getTenantId());
        req.setStartTime(request.getStartTime().toLocalDate());
        req.setEndTime(request.getEndTime().toLocalDate());
        req.planDemand();
        return Optional.ofNullable(inventorySupportService.queryOverduePlanInventory(req))
                .orElse(BigDecimal.ZERO);
    }

}
