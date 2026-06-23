package com.inventory.middle.infra.plan.persistence.mapper;

import com.inventory.middle.infra.plan.persistence.condition.PlanOrderIssueDetailPageCondition;
import com.inventory.middle.infra.plan.persistence.entity.PlanOrderIssueDetailPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PlanOrderIssueDetailMapper {

    int insertSelective(PlanOrderIssueDetailPO record);

    List<PlanOrderIssueDetailPO> pageQueryPlanOrderIssueDetail(PlanOrderIssueDetailPageCondition condition);
}
