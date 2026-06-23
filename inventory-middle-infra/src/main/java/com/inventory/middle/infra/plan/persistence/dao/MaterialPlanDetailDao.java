package com.inventory.middle.infra.plan.persistence.dao;

import com.inventory.middle.domain.plan.common.ex.Checker;
import com.inventory.middle.infra.plan.persistence.mapper.MaterialPlanDetailMapper;
import com.inventory.middle.infra.plan.persistence.entity.MaterialPlanDetailPO;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Repository of {@link MaterialPlanDetailPO}
 *
 * @author Danny.Lee
 * @date 2021/9/29
 */
@Repository
public class MaterialPlanDetailDao {

    @Resource
    private MaterialPlanDetailMapper materialPlanDetailMapper;

    public int insert(MaterialPlanDetailPO record) {
        return materialPlanDetailMapper.insert(record);
    }

    public MaterialPlanDetailPO selectById(Long id) {
        return materialPlanDetailMapper.selectById(id);
    }

    public int update(MaterialPlanDetailPO record) {
        Checker.notNull(record, "record cannot be null");
        Checker.notNull(record.getId(), "record id cannot be null");
        return materialPlanDetailMapper.update(record);
    }

    public List<MaterialPlanDetailPO> selectByInstanceId(Long materialInstanceId){
        return materialPlanDetailMapper.selectByMaterialInstance(materialInstanceId);
    }
}
