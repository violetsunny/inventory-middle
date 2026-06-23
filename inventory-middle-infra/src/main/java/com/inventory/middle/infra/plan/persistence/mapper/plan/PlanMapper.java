package com.inventory.middle.infra.plan.persistence.mapper.plan;

import com.inventory.middle.infra.plan.persistence.condition.plan.*;
import com.inventory.middle.infra.plan.persistence.entity.ChangeStatusPlanPO;
import com.inventory.middle.infra.plan.persistence.entity.PlanPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PlanMapper {
    int insert(PlanPO record);

    int insertSelective(PlanPO record);

    PlanPO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PlanPO record);

    int updateByPrimaryKeyWithBLOBs(PlanPO record);

    int updateByPrimaryKey(PlanPO record);

    List<PlanPO> selectPlanByType(PlanQueryByTypeCondition condition);

    List<PlanPO> pageQueryPlan(PlanPageCondition condition);

    List<PlanPO> selectPlanByIds(List<Long> list);

    List<PlanPO> selectPlanByCoverMaterialType(PlanQueryByCoverMaterialTypeCondition condition);

    List<PlanPO> selectPlanByDemandPlanId(PlanQueryByDemandPlanIdCondition demandPlanId);

    List<PlanPO> selectPlanByLogicalPlantNo(PlanQueryByLogicalPlantNoCondition condition);

    int updateStatusByPrimaryKey(ChangeStatusPlanPO po);

    List<PlanPO> selectByTenantId(@Param("tenantId") String tenantId);

    List<String> selectPlanCondition(List<PlanMaterialQueryByPlanCondition> condition);
}