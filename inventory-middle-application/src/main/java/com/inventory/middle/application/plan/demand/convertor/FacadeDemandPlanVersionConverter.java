package com.inventory.middle.application.plan.demand.convertor;

import com.inventory.middle.client.plan.demand.dto.DemandPlanVersionDTO;
import com.inventory.middle.client.plan.demand.dto.DemandPlanVersionSelectReqDTO;
import com.inventory.middle.client.plan.demand.dto.DemandPlanVersionSelectResDTO;
import com.inventory.middle.application.plan.demand.bo.DemandPlanVersionBO;
import com.inventory.middle.application.plan.demand.bo.DemandPlanVersionSelectReqBO;
import com.inventory.middle.application.plan.demand.bo.DemandPlanVersionSelectResBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FacadeDemandPlanVersionConverter {

    FacadeDemandPlanVersionConverter INSTANCE = Mappers.getMapper(FacadeDemandPlanVersionConverter.class);

    DemandPlanVersionSelectReqBO toReqBO(DemandPlanVersionSelectReqDTO dto);

    DemandPlanVersionSelectResDTO toResDTO(DemandPlanVersionSelectResBO bo);

    DemandPlanVersionDTO toVersionDTO(DemandPlanVersionBO bo);
}
