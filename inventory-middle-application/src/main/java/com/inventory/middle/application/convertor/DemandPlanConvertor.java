package com.inventory.middle.application.convertor;

import com.inventory.middle.application.plan.demand.bo.DemandPlanSelectReqBO;
import com.inventory.middle.infra.plan.persistence.condition.demand.DemandPlanSelectReqCondition;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DemandPlanConvertor {

    DemandPlanSelectReqCondition toCondition(DemandPlanSelectReqBO bo);
}
