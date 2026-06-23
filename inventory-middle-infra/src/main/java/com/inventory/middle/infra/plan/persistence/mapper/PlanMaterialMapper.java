package com.inventory.middle.infra.plan.persistence.mapper;

import com.inventory.middle.infra.plan.persistence.condition.PlanMaterialQueryCondition;
import com.inventory.middle.infra.plan.persistence.entity.PlanMaterialPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PlanMaterialMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PlanMaterialPO record);

    int insertSelective(PlanMaterialPO record);

    PlanMaterialPO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PlanMaterialPO record);

    int updateByPrimaryKey(PlanMaterialPO record);

    List<PlanMaterialPO> selectByMaterialCodeAndLogicalNo(@Param("type")Integer type,@Param("conditions") List<PlanMaterialQueryCondition> conditions);

    void batchInsertPlanMaterial(List<PlanMaterialPO> list);

    int batchDeletePlanMaterial(List<PlanMaterialPO> list);

    List<PlanMaterialPO> selectByPlanId(@Param("sourceId") Long sourceId, @Param("tenantId") String tenantId, @Param("type") Integer type);

    void deletePlanMaterialByPlanId(@Param("sourceId") Long sourceId,@Param("type") Integer type);

    List<PlanMaterialPO> selectByPlanIds(@Param("type") Integer type,@Param("sources")List<Long> list);

    void deletePlanMaterialNotInLogicalPlants(@Param("sourceId") Long sourceId, @Param("type") Integer type, @Param("list") List<String> list);
}