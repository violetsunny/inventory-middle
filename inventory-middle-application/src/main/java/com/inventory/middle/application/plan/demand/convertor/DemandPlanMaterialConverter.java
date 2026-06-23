package com.inventory.middle.application.plan.demand.convertor;

import com.inventory.middle.application.plan.demand.handler.ReverseCalculateMaterial;
import com.inventory.middle.infra.plan.persistence.condition.plan.DemandPlanMaterialDetailReqCondition;
import com.inventory.middle.infra.plan.persistence.entity.DemandPlanMaterialPO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @description:
 * @author:Vincent.Xiao
 * @date:2021/9/29 19:11
 */
@Mapper
public interface DemandPlanMaterialConverter {

    DemandPlanMaterialConverter INSTANCE = Mappers.getMapper(DemandPlanMaterialConverter.class);


    ReverseCalculateMaterial toReverseCalculateMaterial(DemandPlanMaterialPO source);

    List<ReverseCalculateMaterial> toReverseCalculateMaterialList(List<DemandPlanMaterialPO> source);


}
