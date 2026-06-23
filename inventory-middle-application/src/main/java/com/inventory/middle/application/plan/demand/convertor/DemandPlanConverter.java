package com.inventory.middle.application.plan.demand.convertor;

import com.inventory.middle.application.plan.demand.bo.DemandPlanBO;
import com.inventory.middle.application.plan.demand.bo.DemandPlanUpdateStatusBO;
import com.inventory.middle.infra.plan.persistence.entity.DemandPlanPO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @description:
 * @author:Vincent.Xiao
 * @date:2021/9/29 19:11
 */
@Mapper
public interface DemandPlanConverter {

    DemandPlanConverter INSTANCE = Mappers.getMapper(DemandPlanConverter.class);

    DemandPlanPO toPO(DemandPlanUpdateStatusBO source);

    DemandPlanPO bo2PO(DemandPlanBO source);

    DemandPlanBO po2BO(DemandPlanPO source);
}
