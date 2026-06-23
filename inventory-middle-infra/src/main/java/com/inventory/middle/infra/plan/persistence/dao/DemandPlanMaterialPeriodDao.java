package com.inventory.middle.infra.plan.persistence.dao;

import com.inventory.middle.infra.plan.persistence.mapper.DemandPlanMaterialPeriodMapper;
import com.inventory.middle.infra.plan.persistence.entity.DemandPlanMaterialPeriodPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Vincent.Xiao
 * @date 2021/10/9
 */
@Repository
public class DemandPlanMaterialPeriodDao {

    @Autowired
    private DemandPlanMaterialPeriodMapper planMaterialPeriodMapper;

    public List<DemandPlanMaterialPeriodPO> queryByCondition(DemandPlanMaterialPeriodPO record){
        return planMaterialPeriodMapper.queryByCondition(record);
    }

    public DemandPlanMaterialPeriodPO querySingleByCondition(DemandPlanMaterialPeriodPO record){
        return planMaterialPeriodMapper.querySingleByCondition(record);
    }
    public List<DemandPlanMaterialPeriodPO> queryByIds(List<Long> ids){
        return planMaterialPeriodMapper.queryByIds(ids);
    }

    public  int updateByPrimaryKey(DemandPlanMaterialPeriodPO record){
        return planMaterialPeriodMapper.updateByPrimaryKey(record);
    }

    public DemandPlanMaterialPeriodPO queryById(Long id) {
        return planMaterialPeriodMapper.queryById(id);
    }

}
