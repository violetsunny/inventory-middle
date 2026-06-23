package com.inventory.middle.infra.plan.persistence.dao.plan;

import com.inventory.middle.infra.plan.persistence.condition.plan.DemandSourceForPlanOrderCondition;
import com.inventory.middle.infra.plan.persistence.entity.DemandSupplySourcePO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 供需数据 DAO
 * <p>
 * 迁移自 com.enn.plan.management.dal.dao.DemandSupplySourceDao
 * TODO: 需补充 Mapper 实现
 * </p>
 *
 * @author migrated from scm-plan-management
 */
@Component
public class DemandSupplySourceDao {

    /**
     * 根据计划订单需求条件查询供需数据
     *
     * @param condition 查询条件
     * @return 供需数据列表
     */
    public List<DemandSupplySourcePO> queryForPlanOrderDemands(DemandSourceForPlanOrderCondition condition) {
        // TODO: 待补充 Mapper 实现
        throw new UnsupportedOperationException("DemandSupplySourceDao.queryForPlanOrderDemands not implemented yet");
    }

    /**
     * 批量插入更新
     *
     * @param insertList 插入列表
     * @param updateList 更新列表
     */
    @Transactional(rollbackFor = Exception.class)
    public void batchInsertAndUpdate(List<DemandSupplySourcePO> insertList, List<DemandSupplySourcePO> updateList) {
        // TODO: 待补充 Mapper 实现
        throw new UnsupportedOperationException("DemandSupplySourceDao.batchInsertAndUpdate not implemented yet");
    }

}
