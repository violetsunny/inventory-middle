package com.inventory.middle.infra.plan.persistence.mapper.bom;

import com.inventory.middle.infra.plan.persistence.condition.bom.BomCaseQueryCondition;
import com.inventory.middle.infra.plan.persistence.condition.bom.BomChangeStatusCondition;
import com.inventory.middle.infra.plan.persistence.condition.bom.BomCodeQueryCondition;
import com.inventory.middle.infra.plan.persistence.condition.bom.BomCaseResult;
import com.inventory.middle.infra.plan.persistence.entity.bom.BomCasePO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BomCaseMapper {

    int deleteByPrimaryKey(Long id);

    int insert(BomCasePO record);

    int insertSelective(BomCasePO record);

    BomCasePO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BomCasePO record);

    int updateByPrimaryKey(BomCasePO record);

    int updateStatusById(BomChangeStatusCondition condition);

    List<BomCaseResult> pageQueryBom(BomCaseQueryCondition condition);

    List<BomCasePO> queryByCondition(BomCasePO condition);

    List<BomCaseResult> queryBomCode(BomCodeQueryCondition condition);
}
