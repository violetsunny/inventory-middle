package com.inventory.middle.application.plan.demand.convertor;

import com.inventory.middle.client.plan.demand.dto.DemandPlanMaterialBatchCreateReqDTO;
import com.inventory.middle.application.plan.demand.bo.DemandPlanMaterialBatchCreateReqBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @description:
 * @author:Vincent.Xiao
 * @date:2021/9/29 19:11
 */
@Mapper
public interface DemandPlanMaterialBatchCreateReqConverter {

    DemandPlanMaterialBatchCreateReqConverter INSTANCE = Mappers.getMapper(DemandPlanMaterialBatchCreateReqConverter.class);

    DemandPlanMaterialBatchCreateReqBO toBO(DemandPlanMaterialBatchCreateReqDTO source);

}
