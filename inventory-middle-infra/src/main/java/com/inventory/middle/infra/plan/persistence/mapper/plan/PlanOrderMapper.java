package com.inventory.middle.infra.plan.persistence.mapper.plan;

import com.inventory.middle.infra.plan.persistence.condition.plan.PlanOrderCondition;
import com.inventory.middle.infra.plan.persistence.condition.plan.PlanOrderPageCondition;
import com.inventory.middle.infra.plan.persistence.entity.PlanOrderPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PlanOrderMapper {

    int insertSelective(PlanOrderPO record);

    int updateByPrimaryKeySelective(PlanOrderPO record);

    PlanOrderPO selectByIdAndTenantId(@Param("id") Long id, @Param("tenantId") String tenantId);

    List<PlanOrderPO> pageQueryPlanOrder(PlanOrderPageCondition condition);

    List<PlanOrderPO> listByCondition(PlanOrderCondition condition);

    int confirmPlanOrder(@Param("id") Long id);
}
