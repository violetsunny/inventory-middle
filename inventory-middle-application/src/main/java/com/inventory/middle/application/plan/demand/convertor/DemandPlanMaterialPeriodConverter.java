package com.inventory.middle.application.plan.demand.convertor;

import com.inventory.middle.application.plan.demand.bo.DemandPlanMaterialBatchCreateDetailBO;
import com.inventory.middle.application.plan.demand.bo.DemandPlanPeriodInfoBO;
import com.inventory.middle.infra.plan.persistence.entity.DemandPlanMaterialDetailPO;
import com.inventory.middle.infra.plan.persistence.entity.DemandPlanMaterialPeriodPO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @description:
 * @author:Vincent.Xiao
 * @date:2021/9/29 19:11
 */
@Mapper
public interface DemandPlanMaterialPeriodConverter {

    DemandPlanMaterialPeriodConverter INSTANCE = Mappers.getMapper(DemandPlanMaterialPeriodConverter.class);


    DemandPlanPeriodInfoBO toInfoBO(DemandPlanMaterialPeriodPO source);


}
