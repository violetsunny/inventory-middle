package com.inventory.middle.application.plan.demand.convertor;

import com.inventory.middle.application.plan.demand.bo.DemandPlanDetailResultBO;
import com.inventory.middle.application.plan.demand.bo.PeriodDemandResultBO;
import com.inventory.middle.infra.plan.persistence.result.DemandPlanDetailResult;
import com.inventory.middle.infra.plan.persistence.result.PeriodDemandResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author zhouxinzhong
 * @date 2021/10/11 10:40
 */
@Mapper
public interface DemandPlanDetailConverter {

    DemandPlanDetailConverter INSTANCE = Mappers.getMapper(DemandPlanDetailConverter.class);

    List<DemandPlanDetailResultBO> toResultBOList(List<DemandPlanDetailResult> resultList);

    PeriodDemandResultBO toPeriodResultBO(PeriodDemandResult result);
    
}
