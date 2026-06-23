package com.inventory.middle.application.plan.demand.convertor;

import com.inventory.middle.application.plan.demand.handler.DemandPlanMaterialAmount;
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
public interface DemandPlanAmountRequestConverter {

    DemandPlanAmountRequestConverter INSTANCE = Mappers.getMapper(DemandPlanAmountRequestConverter.class);


    DemandPlanMaterialDetailReqCondition toCondition(DemandPlanMaterialAmount source);

    List<DemandPlanMaterialDetailReqCondition> toConditionPOList(List<DemandPlanMaterialAmount> source);


}
