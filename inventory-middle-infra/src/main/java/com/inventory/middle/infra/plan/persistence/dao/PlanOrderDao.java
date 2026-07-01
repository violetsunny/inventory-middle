package com.inventory.middle.infra.plan.persistence.dao;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.inventory.middle.infra.plan.persistence.condition.PlanOrderCondition;
import com.inventory.middle.infra.plan.persistence.condition.PlanOrderIssueDetailPageCondition;
import com.inventory.middle.infra.plan.persistence.condition.PlanOrderPageCondition;
import com.inventory.middle.infra.plan.persistence.entity.PlanOrderIssueDetailPO;
import com.inventory.middle.infra.plan.persistence.entity.PlanOrderPO;
import com.inventory.middle.infra.plan.persistence.mapper.PlanOrderIssueDetailMapper;
import com.inventory.middle.infra.plan.persistence.mapper.PlanOrderMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 计划订单 DAO。
 */
@Component
public class PlanOrderDao {

    @Resource
    private PlanOrderMapper planOrderMapper;

    @Resource
    private PlanOrderIssueDetailMapper planOrderIssueDetailMapper;

    public Boolean createManualPlanOrder(PlanOrderPO po) {
        return planOrderMapper.insertSelective(po) > 0;
    }

    public PlanOrderPO queryPlanOrderById(Long id, String tenantId) {
        return planOrderMapper.selectByIdAndTenantId(id, tenantId);
    }

    public Boolean updatePlanOrder(PlanOrderPO po) {
        return planOrderMapper.updateByPrimaryKeySelective(po) > 0;
    }

    public PageInfo<PlanOrderPO> pageQueryPlanOrder(PlanOrderPageCondition condition) {
        PageHelper.startPage(condition.getPageNum(), condition.getSize());
        return new PageInfo<>(planOrderMapper.pageQueryPlanOrder(condition));
    }

    public Boolean confirmPlanOrder(Long id) {
        return planOrderMapper.confirmPlanOrder(id) > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean issuePlanOrder(PlanOrderPO planOrderPO, PlanOrderIssueDetailPO issueDetailPO) {
        int updateResult = planOrderMapper.updateByPrimaryKeySelective(planOrderPO);
        int insertResult = planOrderIssueDetailMapper.insertSelective(issueDetailPO);
        return updateResult > 0 && insertResult > 0;
    }

    public List<PlanOrderPO> listOrdersByCondition(PlanOrderCondition condition) {
        return planOrderMapper.listByCondition(condition);
    }

    public PageInfo<PlanOrderIssueDetailPO> pageQueryPlanOrderIssueDetail(PlanOrderIssueDetailPageCondition condition) {
        PageHelper.startPage(condition.getPageNum(), condition.getSize());
        return new PageInfo<>(planOrderIssueDetailMapper.pageQueryPlanOrderIssueDetail(condition));
    }

    public List<PlanOrderPO> queryOverduePlanOrder() {
        return planOrderMapper.queryOverduePlanOrder();
    }

    public Boolean changePlanOrderStatus(List<Long> ids) {
        return planOrderMapper.batchUpdateStatusToFinishOverdue(ids) > 0;
    }
}
