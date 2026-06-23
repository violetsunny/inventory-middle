package com.inventory.middle.application.plan.demand.convertor;

import com.inventory.middle.client.plan.demand.dto.DemandPlanSelectReqDTO;
import com.inventory.middle.client.plan.demand.dto.DemandPlanSelectResDTO;
import com.inventory.middle.application.plan.demand.bo.DemandPlanSelectReqBO;
import com.inventory.middle.application.plan.demand.bo.DemandPlanSelectResBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface FacadeDemandPlanSelectConverter {

    FacadeDemandPlanSelectConverter INSTANCE = Mappers.getMapper(FacadeDemandPlanSelectConverter.class);

    DemandPlanSelectReqBO toReqBO(DemandPlanSelectReqDTO reqDTO);

    List<DemandPlanSelectResDTO> toResDTOList(List<DemandPlanSelectResBO> BOList);
}
