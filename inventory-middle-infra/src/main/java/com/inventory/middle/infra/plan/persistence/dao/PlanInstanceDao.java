package com.inventory.middle.infra.plan.persistence.dao;

import com.inventory.middle.domain.plan.common.ex.Checker;
import com.inventory.middle.infra.plan.persistence.mapper.PlanInstanceMapper;
import com.inventory.middle.infra.plan.persistence.entity.PlanInstancePO;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * Repository of {@link PlanInstancePO}
 *
 * @author Danny.Lee
 * @date 2021/9/29
 */
@Repository
public class PlanInstanceDao {

    @Resource
    private PlanInstanceMapper planInstanceMapper;

    public int insert(PlanInstancePO record) {
        return planInstanceMapper.insert(record);
    }

    public PlanInstancePO selectById(Long id) {
        return planInstanceMapper.selectById(id);
    }

    public PlanInstancePO selectLatestByPlanId(Long planId) {
        return planInstanceMapper.selectLatestByPlanId(planId);
    }

    public int update(PlanInstancePO record) {
        Checker.notNull(record, "record cannot be null");
        Checker.notNull(record.getId(), "record id cannot be null");
        return planInstanceMapper.update(record);
    }
}
