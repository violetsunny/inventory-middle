package com.inventory.middle.infra.plan.persistence.mapper;

import com.inventory.middle.infra.plan.persistence.entity.DemandPlanMaterialPeriodPO;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface DemandPlanMaterialPeriodMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DemandPlanMaterialPeriodPO record);

    int insertSelective(DemandPlanMaterialPeriodPO record);

    DemandPlanMaterialPeriodPO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DemandPlanMaterialPeriodPO record);

    int updateByPrimaryKey(DemandPlanMaterialPeriodPO record);

    int batchInsert(@Param("records") List<DemandPlanMaterialPeriodPO> records);

    int updateBatch(@Param("records") List<DemandPlanMaterialPeriodPO> records);

    int deleteByCondition(DemandPlanMaterialPeriodPO record);

    List<DemandPlanMaterialPeriodPO> queryByCondition(DemandPlanMaterialPeriodPO record);

    int updateStatusByCondition(@Param("record") DemandPlanMaterialPeriodPO condition, @Param("targetStatus") int targetStatus);

    List<DemandPlanMaterialPeriodPO> queryByIds(@Param("ids") List<Long> ids);

    DemandPlanMaterialPeriodPO querySingleByCondition(DemandPlanMaterialPeriodPO record);

    DemandPlanMaterialPeriodPO queryById(@Param("id") Long id);

    List<DemandPlanMaterialPeriodPO> queryByPlanIdAndMaterialCode(@Param("demandPlanId") Long demandPlanId,@Param("materialCodeList") List<String> materialCodeList);
}
