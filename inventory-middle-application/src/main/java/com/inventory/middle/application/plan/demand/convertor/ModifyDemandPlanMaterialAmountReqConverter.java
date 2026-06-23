package com.inventory.middle.application.plan.demand.convertor;

import com.inventory.middle.client.plan.demand.dto.CancelDemandPlanMaterialReqDTO;
import com.inventory.middle.client.plan.demand.dto.ModifyDemandPlanMaterialAmountReqDTO;
import com.inventory.middle.application.plan.demand.bo.CancelDemandPlanMaterialReqBO;
import com.inventory.middle.application.plan.demand.bo.ModifyDemandPlanMaterialAmountReqBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @description:
 * @author:Vincent.Xiao
 * @date:2021/9/29 19:11
 */
@Mapper
public interface ModifyDemandPlanMaterialAmountReqConverter {

    ModifyDemandPlanMaterialAmountReqConverter INSTANCE = Mappers.getMapper(ModifyDemandPlanMaterialAmountReqConverter.class);

    ModifyDemandPlanMaterialAmountReqBO toBO(ModifyDemandPlanMaterialAmountReqDTO source);

}
