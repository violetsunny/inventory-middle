package com.inventory.middle.infra.plan.persistence.mapper.plan;

import com.inventory.middle.infra.plan.persistence.condition.plan.PlanMaterialParamPageCondition;
import com.inventory.middle.infra.plan.persistence.condition.plan.PlanMaterialParamQueryCondition;
import com.inventory.middle.infra.plan.persistence.condition.plan.PlanQueryPlanTransferLogicalPlantsCondition;
import com.inventory.middle.infra.plan.persistence.entity.PlanMaterialParameterPO;
import com.inventory.middle.infra.plan.persistence.entity.PlanPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PlanMaterialParameterMapper {
    int insert(PlanMaterialParameterPO record);

    int insertSelective(PlanMaterialParameterPO record);

    PlanMaterialParameterPO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PlanMaterialParameterPO record);

    int updateByPrimaryKey(PlanMaterialParameterPO record);

    void batchSavePlanMaterialParam(List<PlanMaterialParameterPO> list);

    PlanMaterialParameterPO selectByMaterialCodeAndLogicalPlantNo(PlanMaterialParamQueryCondition condition);

    List<PlanMaterialParameterPO> pageQueryPlanMaterialParam(PlanMaterialParamPageCondition condition);

    List<PlanMaterialParameterPO> batchQueryPlanMaterialParam(List<PlanMaterialParamQueryCondition> conditions);

    List<String> queryLogicalPlantNoByMaterialCode(PlanQueryPlanTransferLogicalPlantsCondition condition);
}