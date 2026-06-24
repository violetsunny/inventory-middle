package com.inventory.middle.infra.plan.persistence.dao;

import com.inventory.middle.infra.plan.persistence.entity.PlanDemandSupplyStockPO;
import com.inventory.middle.infra.plan.persistence.mapper.PlanDemandSupplyStockMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Repository
public class PlanDemandSupplyStockDao {

    @Resource
    private PlanDemandSupplyStockMapper planDemandSupplyStockMapper;

    public int batchInsert(List<PlanDemandSupplyStockPO> poList) {
        return planDemandSupplyStockMapper.batchInsert(poList);
    }

    public int batchUpdate(List<PlanDemandSupplyStockPO> updateList) {
        return planDemandSupplyStockMapper.batchUpdate(updateList);
    }

    @Transactional(rollbackFor = Exception.class)
    public void batchInsertAndUpdate(List<PlanDemandSupplyStockPO> insertList, List<PlanDemandSupplyStockPO> updateList) {
        if (CollectionUtils.isNotEmpty(insertList)) {
            this.batchInsert(insertList);
        }
        if (CollectionUtils.isNotEmpty(updateList)) {
            this.batchUpdate(updateList);
        }
    }

    public List<PlanDemandSupplyStockPO> batchQueryByMaterialCode(String tenantId, String logicalPlantNo,
                                                                    List<String> materialCodeList) {
        return planDemandSupplyStockMapper.batchQueryByMaterialCode(tenantId, logicalPlantNo, materialCodeList);
    }

    public List<PlanDemandSupplyStockPO> queryByPlanDate(String tenantId, String logicalPlantNo,
                                                          String materialCode, Date startDate, Date endDate) {
        return planDemandSupplyStockMapper.queryByPlanDate(tenantId, logicalPlantNo, materialCode, startDate, endDate);
    }

    public List<PlanDemandSupplyStockPO> queryMaterialRecords(String tenantId, String logicalPlantNo,
                                                               String materialCode) {
        return planDemandSupplyStockMapper.queryMaterialRecords(tenantId, logicalPlantNo, materialCode);
    }
}
