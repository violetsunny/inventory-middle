package com.inventory.middle.infra.plan.persistence.mapper;

import com.inventory.middle.infra.plan.persistence.condition.demand.*;
import com.inventory.middle.infra.plan.persistence.condition.DemandPlanMaterialDetailReqCondition;
import com.inventory.middle.infra.plan.persistence.result.DemandDetailResult;
import com.inventory.middle.infra.plan.persistence.result.MaterialResult;
import com.inventory.middle.infra.plan.persistence.result.SingleDemandResult;
import com.inventory.middle.infra.plan.persistence.entity.DemandPlanMaterialDetailPO;
import com.inventory.middle.infra.plan.persistence.entity.DemandPlanMaterialDetailWithExtInfoPO;
import com.inventory.middle.infra.plan.persistence.entity.PlanMaterialPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface DemandPlanMaterialDetailMapper {
    int insert(DemandPlanMaterialDetailPO record);

    int insertSelective(DemandPlanMaterialDetailPO record);

    DemandPlanMaterialDetailPO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DemandPlanMaterialDetailPO record);

    int updateByPrimaryKey(DemandPlanMaterialDetailPO record);

    //根据物料编码、逻辑仓编码、租户id查询记录
    List<DemandPlanMaterialDetailPO> selectByMultiple(DemandPlanMaterialDetailPO record);

    List<DemandPlanMaterialDetailPO> findDetailsByCondition(@Param("records") List<DemandPlanMaterialDetailReqCondition> condition);

    int batchInsert(@Param("records") List<DemandPlanMaterialDetailPO> records);

    int updateAmountBatch(@Param("records") List<DemandPlanMaterialDetailPO> records);

    //获取总数
    long selectCount(@Param("logicalPlantNo") String logicalPlantNo,
                     @Param("materialCodeList") List<String> materialCodeList,
                     @Param("tenantId") String tenantId);

    List<PlanMaterialPO> selectMaterials(DemandBoardMaterialCondition condition);

    List<DemandPlanMaterialDetailWithExtInfoPO> selectDemandBoardResult(DemandBoardDetailCondition condition);

    List<MaterialResult> selectMaterialsByName(MaterialReqCondition reqCondition);

    List<DemandPlanMaterialDetailPO> queryAllMaterial();

    List<DemandPlanMaterialDetailPO> selectMaterialInfo(DemandBoardDetailCondition condition);
}
