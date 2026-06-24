package com.inventory.middle.infra.plan.persistence.mapper;

import com.inventory.middle.infra.plan.persistence.condition.DemandSourceForPlanOrderCondition;
import com.inventory.middle.infra.plan.persistence.entity.DemandSupplySourcePO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface DemandSupplySourceMapper {

    int insert(DemandSupplySourcePO record);

    int insertSelective(DemandSupplySourcePO record);

    DemandSupplySourcePO selectByPrimaryKey(Long id);

    List<DemandSupplySourcePO> queryByCondition(DemandSupplySourcePO record);

    int updateByPrimaryKeySelective(DemandSupplySourcePO record);

    int updateByPrimaryKey(DemandSupplySourcePO record);

    int batchInsert(@Param("records") List<DemandSupplySourcePO> poList);

    List<DemandSupplySourcePO> queryDemandSupply(@Param("tenantId") String tenantId,
                                                  @Param("logicalPlantNo") String logicalPlantNo,
                                                  @Param("materialCodeList") List<String> materialCodeList,
                                                  @Param("startDate") Date startDate,
                                                  @Param("endDate") Date endDate);

    List<DemandSupplySourcePO> queryDemandSupplyByMaterial(@Param("tenantId") String tenantId,
                                                            @Param("logicalPlantNo") String logicalPlantNo,
                                                            @Param("materialCodeList") List<String> materialCodeList);

    int batchUpdate(@Param("records") List<DemandSupplySourcePO> updateList);

    List<DemandSupplySourcePO> queryForPlanOrderDemands(DemandSourceForPlanOrderCondition condition);
}
