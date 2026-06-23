package com.inventory.middle.application.plan.demand.convertor;

import com.inventory.middle.application.plan.demand.handler.ReverseCalculateMaterial;
import com.inventory.middle.infra.plan.persistence.condition.DemandPlanMaterialDetailReqCondition;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @description:
 * @author:Vincent.Xiao
 * @date:2021/9/29 19:11
 */
@Mapper
public interface ReverseCalculateRequestConverter {

    ReverseCalculateRequestConverter INSTANCE = Mappers.getMapper(ReverseCalculateRequestConverter.class);


    DemandPlanMaterialDetailReqCondition toCondition(ReverseCalculateMaterial source);

    List<DemandPlanMaterialDetailReqCondition> toConditionPOList(List<ReverseCalculateMaterial> source);


}
