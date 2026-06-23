package com.inventory.middle.application.plan.demand.convertor;

import com.inventory.middle.client.plan.demand.dto.DemandPlanCreateReqDTO;
import com.inventory.middle.application.plan.demand.bo.DemandPlanBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @description:
 * @author:Vincent.Xiao
 * @date:2021/9/29 19:11
 */
@Mapper
public interface DemandPlanCreateReqConverter {

    DemandPlanCreateReqConverter INSTANCE = Mappers.getMapper(DemandPlanCreateReqConverter.class);

    DemandPlanBO toBO(DemandPlanCreateReqDTO source);

}
