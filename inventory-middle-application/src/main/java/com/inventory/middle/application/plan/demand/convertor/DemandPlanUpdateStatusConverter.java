package com.inventory.middle.application.plan.demand.convertor;

import com.inventory.middle.client.plan.demand.dto.DemandPlanMaterialBatchCreateResDTO;
import com.inventory.middle.client.plan.demand.dto.DemandPlanUpdateStatusDTO;
import com.inventory.middle.application.plan.demand.bo.DemandPlanMaterialBatchCreateResBO;
import com.inventory.middle.application.plan.demand.bo.DemandPlanUpdateStatusBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @description:
 * @author:Vincent.Xiao
 * @date:2021/9/29 19:11
 */
@Mapper
public interface DemandPlanUpdateStatusConverter {

    DemandPlanUpdateStatusConverter INSTANCE = Mappers.getMapper(DemandPlanUpdateStatusConverter.class);

    DemandPlanUpdateStatusBO toBO(DemandPlanUpdateStatusDTO source);

}
