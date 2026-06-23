package com.inventory.middle.infra.plan.persistence.mapper.bom;

import com.inventory.middle.infra.plan.persistence.condition.bom.BomChangeStatusCondition;
import com.inventory.middle.infra.plan.persistence.entity.bom.BomNodePO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BomNodeMapper {

    int deleteByPrimaryKey(Long id);

    int insert(BomNodePO record);

    int insertSelective(BomNodePO record);

    BomNodePO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BomNodePO record);

    int updateByPrimaryKey(BomNodePO record);

    List<BomNodePO> queryByCondition(BomNodePO bomNodePO);

    BomNodePO querySingleByCondition(BomNodePO bomNodePO);

    List<BomNodePO> queryNodeAsChild(BomNodePO condition);

    int batchInsert(List<BomNodePO> list);

    int deleteByBomCaseId(Long id);

    List<String> queryNodeByBomCaseId(BomNodePO bomNodePO);

    int changeNodeStatus(BomChangeStatusCondition condition);

    List<BomNodePO> batchQueryBomNode(List<BomNodePO> conditions);
}
