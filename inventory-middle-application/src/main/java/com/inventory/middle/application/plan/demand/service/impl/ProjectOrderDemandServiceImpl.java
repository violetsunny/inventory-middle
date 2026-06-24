package com.inventory.middle.application.plan.demand.service.impl;

import com.inventory.middle.application.plan.demand.bo.OrderDemandBO;
import com.inventory.middle.application.plan.demand.service.ProjectOrderDemandService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProjectOrderDemandServiceImpl implements ProjectOrderDemandService {

    @Override
    public boolean insertProjectOrderPlan(List<OrderDemandBO> orderPlanBOList) {
        if (CollectionUtils.isEmpty(orderPlanBOList)) {
            return false;
        }
        log.info("insertProjectOrderPlan size={}, firstOrderNo={}", orderPlanBOList.size(),
                orderPlanBOList.get(0).getOrderNo());
        return true;
    }
}
