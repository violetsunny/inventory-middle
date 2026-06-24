package com.inventory.middle.infra.plan.persistence.mapper;

import com.inventory.middle.infra.plan.persistence.entity.PlanDemandSupplyStockPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface PlanDemandSupplyStockMapper {

    int insert(PlanDemandSupplyStockPO record);

    int insertSelective(PlanDemandSupplyStockPO record);

    PlanDemandSupplyStockPO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PlanDemandSupplyStockPO record);

    int updateByPrimaryKey(PlanDemandSupplyStockPO record);

    int batchInsert(@Param("records") List<PlanDemandSupplyStockPO> poList);

    List<PlanDemandSupplyStockPO> batchQueryByMaterialCode(@Param("tenantId") String tenantId,
                                                            @Param("logicalPlantNo") String logicalPlantNo,
                                                            @Param("materialCodeList") List<String> materialCodeList);

    int batchUpdate(@Param("records") List<PlanDemandSupplyStockPO> updateList);

    List<PlanDemandSupplyStockPO> queryByPlanDate(@Param("tenantId") String tenantId,
                                                   @Param("logicalPlantNo") String logicalPlantNo,
                                                   @Param("materialCode") String materialCode,
                                                   @Param("startDate") Date startDate,
                                                   @Param("endDate") Date endDate);

    List<PlanDemandSupplyStockPO> queryMaterialRecords(@Param("tenantId") String tenantId,
                                                        @Param("logicalPlantNo") String logicalPlantNo,
                                                        @Param("materialCode") String materialCode);
}
