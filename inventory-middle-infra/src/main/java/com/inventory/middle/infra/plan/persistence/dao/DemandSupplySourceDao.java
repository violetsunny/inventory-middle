package com.inventory.middle.infra.plan.persistence.dao;

import com.inventory.middle.infra.plan.persistence.condition.DemandSourceForPlanOrderCondition;
import com.inventory.middle.infra.plan.persistence.entity.DemandSupplySourcePO;
import com.inventory.middle.infra.plan.persistence.mapper.DemandSupplySourceMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Repository
public class DemandSupplySourceDao {

    @Resource
    private DemandSupplySourceMapper demandSupplySourceMapper;

    public int insert(DemandSupplySourcePO record) {
        return demandSupplySourceMapper.insertSelective(record);
    }

    public int updateById(DemandSupplySourcePO record) {
        return demandSupplySourceMapper.updateByPrimaryKeySelective(record);
    }

    public int batchInsert(List<DemandSupplySourcePO> poList) {
        return demandSupplySourceMapper.batchInsert(poList);
    }

    public int batchUpdate(List<DemandSupplySourcePO> updateList) {
        return demandSupplySourceMapper.batchUpdate(updateList);
    }

    @Transactional(rollbackFor = Exception.class)
    public void batchInsertAndUpdate(List<DemandSupplySourcePO> insertList, List<DemandSupplySourcePO> updateList) {
        if (CollectionUtils.isNotEmpty(insertList)) {
            this.batchInsert(insertList);
        }
        if (CollectionUtils.isNotEmpty(updateList)) {
            this.batchUpdate(updateList);
        }
    }

    public List<DemandSupplySourcePO> queryDemandSupply(String tenantId, String logicalPlantNo,
                                                         List<String> materialCodeList,
                                                         Date startDate, Date endDate) {
        return demandSupplySourceMapper.queryDemandSupply(tenantId, logicalPlantNo, materialCodeList, startDate, endDate);
    }

    public List<DemandSupplySourcePO> queryDemandSupplyByMaterial(String tenantId, String logicalPlantNo,
                                                                    List<String> materialCodeList) {
        return demandSupplySourceMapper.queryDemandSupplyByMaterial(tenantId, logicalPlantNo, materialCodeList);
    }

    public List<DemandSupplySourcePO> queryByCondition(DemandSupplySourcePO record) {
        return demandSupplySourceMapper.queryByCondition(record);
    }

    public List<DemandSupplySourcePO> queryForPlanOrderDemands(DemandSourceForPlanOrderCondition condition) {
        return demandSupplySourceMapper.queryForPlanOrderDemands(condition);
    }
}
