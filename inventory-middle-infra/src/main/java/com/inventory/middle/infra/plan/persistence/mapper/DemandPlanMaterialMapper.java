package com.inventory.middle.infra.plan.persistence.mapper;

import com.inventory.middle.infra.plan.persistence.result.DemandDetailResult;
import com.inventory.middle.infra.plan.persistence.result.DemandPlanDetailResult;
import com.inventory.middle.infra.plan.persistence.entity.DemandPlanMaterialPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DemandPlanMaterialMapper {
    int insert(DemandPlanMaterialPO record);

    int insertSelective(DemandPlanMaterialPO record);

    DemandPlanMaterialPO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DemandPlanMaterialPO record);

    int updateByPrimaryKey(DemandPlanMaterialPO record);

    List<DemandPlanMaterialPO> selectValidMaterialByPlanId(Long id);

    List<DemandPlanMaterialPO> selectByCondition(DemandPlanMaterialPO record);

    DemandPlanMaterialPO selectMaterialByCode(
            @Param("materialCode") String materialCode,
            @Param("logicalPlantNo") String logicalPlantNo,
            @Param("tenantId") String tenantId);

    List<DemandPlanDetailResult> selectMaterialsInfo(@Param("demandPlanId") Long demandPlanId,
                                                     @Param("tenantId") String tenantId);

    int updateStatusByCondition(@Param("record") DemandPlanMaterialPO condition, @Param("targetStatus") int targetStatus);

    int deleteByPlanIdAndMaterialCode(@Param("demandPlanId") Long demandPlanId,@Param("materialCodeList") List<String> materialCodeList);
}
