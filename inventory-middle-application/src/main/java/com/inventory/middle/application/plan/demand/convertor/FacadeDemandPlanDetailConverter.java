package com.inventory.middle.application.plan.demand.convertor;

import com.inventory.middle.client.plan.demand.dto.DemandPlanDetailResultDTO;
import com.inventory.middle.client.plan.demand.dto.DemandPlanDetailSelectReqDTO;
import com.inventory.middle.client.plan.demand.dto.DemandPlanDetailSelectResDTO;
import com.inventory.middle.client.plan.demand.dto.PeriodDemandResultDTO;
import com.inventory.middle.application.plan.demand.bo.DemandPlanDetailResultBO;
import com.inventory.middle.application.plan.demand.bo.DemandPlanDetailSelectReqBO;
import com.inventory.middle.application.plan.demand.bo.DemandPlanDetailSelectResBO;
import com.inventory.middle.application.plan.demand.bo.PeriodDemandResultBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FacadeDemandPlanDetailConverter {

    FacadeDemandPlanDetailConverter INSTANCE = Mappers.getMapper(FacadeDemandPlanDetailConverter.class);

    DemandPlanDetailSelectReqBO toReqBO(DemandPlanDetailSelectReqDTO reqDTO);

    DemandPlanDetailSelectResDTO toResDTO(DemandPlanDetailSelectResBO resBO);

    DemandPlanDetailResultDTO toResultDTO(DemandPlanDetailResultBO resultBO);

    PeriodDemandResultDTO toPeriodDTO(PeriodDemandResultBO periodBO);

}
