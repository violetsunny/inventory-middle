package com.inventory.middle.application.plan.demand.convertor;

import com.inventory.middle.application.plan.demand.bo.DemandPlanMaterialAmountBO;
import com.inventory.middle.infra.plan.persistence.entity.DemandPlanMaterialDetailPO;
import com.inventory.middle.infra.plan.persistence.entity.DemandPlanMaterialPeriodPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @description:
 * @author:Vincent.Xiao
 * @date:2021/9/29 19:11
 */
@Mapper
public interface DemandPlanMaterialDetailConverter {

    DemandPlanMaterialDetailConverter INSTANCE = Mappers.getMapper(DemandPlanMaterialDetailConverter.class);


    DemandPlanMaterialDetailPO toDetail(DemandPlanMaterialPeriodPO source);


}
