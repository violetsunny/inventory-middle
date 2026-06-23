package com.inventory.middle.infra.plan.persistence.mapper;

import com.inventory.middle.infra.plan.persistence.condition.demand.DemandPlanSelectReqCondition;
import com.inventory.middle.infra.plan.persistence.entity.DemandPlanPO;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface DemandPlanMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DemandPlanPO record);

    int insertSelective(DemandPlanPO record);

    DemandPlanPO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DemandPlanPO record);

    int updateByPrimaryKey(DemandPlanPO record);

    List<DemandPlanPO> selectByIds(@Param("ids") List<Long> ids);

    List<DemandPlanPO> selectByPage(DemandPlanSelectReqCondition reqCondition);

    List<DemandPlanPO> selectByLogicalPlantNos(@Param("logicalPlantNos") List<String> logicalPlantNos,
                                               @Param("tenantId") String tenantId);

    List<String> checkDuplicate(@Param("tenantId") String tenantId,@Param("demandType") int demandType,  @Param("logicalPlantNo") String logicalPlantNo, @Param("materialList") List<String> materialList);

    DemandPlanPO selectPlanInfo(@Param("id") Long id, @Param("tenantId") String tenantId);

    DemandPlanPO querySingleByCondition(DemandPlanSelectReqCondition reqCondition);

}
