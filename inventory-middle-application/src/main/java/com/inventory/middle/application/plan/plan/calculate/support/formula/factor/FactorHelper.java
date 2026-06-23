package com.inventory.middle.application.plan.plan.calculate.support.formula.factor;

import com.inventory.middle.infra.plan.util.DateUtils;
import com.inventory.middle.application.plan.plan.calculate.convert.PlanGenerateConverter;
import com.inventory.middle.application.plan.plan.calculate.support.generate.BomMaterialPlanGeneContext;
import com.inventory.middle.application.plan.plan.calculate.support.generate.MaterialPlanGeneContext;
import com.inventory.middle.application.plan.plan.config.bo.PlanBO;
import com.inventory.middle.application.plan.plan.config.bo.PlanMaterialParameterBO;
import com.inventory.middle.infra.plan.persistence.dao.DemandPlanMaterialDetailDao;
import com.inventory.middle.infra.plan.persistence.entity.DemandPlanMaterialDetailPO;
import com.inventory.middle.application.plan.support.InventorySupportService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * 物料计算因子构建帮助类
 *
 * @author Danny.Lee
 * @date 2021/10/19
 */
@Component
public class FactorHelper {

    @Resource
    private InventorySupportService inventorySupportService;

    @Resource
    private DemandPlanMaterialDetailDao demandPlanMaterialDetailDao;

    @Resource
    private PlanGenerateConverter planGenerateConverter;

    public MaterialFactor buildFactor(MaterialPlanGeneContext context) {
        final String materialCode = context.getMaterial().getMaterialCode();
        final String logicalPlantNo = context.getMaterial().getLogicalPlantNo();
        final String tenantId = context.getMaterial().getTenantId();

        MaterialFactor factor = new MaterialFactor();
        factor.setMaterialCode(materialCode);
        factor.setLogicalPlantNo(logicalPlantNo);
        factor.setTenantId(tenantId);
        factor.setPlanStartDate(context.planStartDate());
        factor.setPlanEndDate(context.planEndDate());

        factor.setCurrentStock(context.getInitialStock());

        List<DemandPlanMaterialDetailPO> materialDemandDetails =
                demandPlanMaterialDetailDao.findMaterialDemandDetails(materialCode, logicalPlantNo, tenantId);
        factor.setDemandFactor(
                planGenerateConverter.convertDemandFactor(materialDemandDetails));
        factor.setParameterFactor(
                planGenerateConverter.convertParameterFactor(context.getPlan(), context.getPlanMaterialParameter()));

        if (context instanceof BomMaterialPlanGeneContext) {
            BomMaterialPlanGeneContext bomContext = (BomMaterialPlanGeneContext) context;
            factor.setBomFactor(planGenerateConverter.convertBomFactor(bomContext.getAmount(), bomContext.getParent()));
        }

        return factor;
    }

    public MaterialFactor buildFactor(PlanBO plan, PlanMaterialParameterBO parameter, LocalDate planDate) {
        final String materialCode = parameter.getMaterialCode();
        final String logicalPlantNo = parameter.getLogicalPlantNo();
        final String tenantId = parameter.getTenantId();

        MaterialFactor factor = new MaterialFactor();
        factor.setMaterialCode(materialCode);
        factor.setLogicalPlantNo(logicalPlantNo);
        factor.setTenantId(tenantId);
        factor.setPlanDate(planDate);
        factor.setPlanStartDate(LocalDate.now().plusDays(1));
        factor.setPlanEndDate(DateUtils.toLocalDate(plan.getPlanEndTime()));

        BigDecimal initialStock = Optional.ofNullable(inventorySupportService.queryInventory(
                materialCode, logicalPlantNo, tenantId)).orElse(BigDecimal.ZERO);
        factor.setCurrentStock(initialStock);

        List<DemandPlanMaterialDetailPO> materialDemandDetails =
                demandPlanMaterialDetailDao.findMaterialDemandDetails(materialCode, logicalPlantNo, tenantId);
        factor.setDemandFactor(
                planGenerateConverter.convertDemandFactor(materialDemandDetails));
        factor.setParameterFactor(
                planGenerateConverter.convertParameterFactor(plan, parameter));
        return factor;
    }
}
