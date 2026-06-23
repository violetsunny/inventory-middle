package com.inventory.middle.application.plan.demand.convertor;

import com.inventory.middle.application.plan.demand.bo.OrderDemandBO;
import com.inventory.middle.infra.plan.persistence.entity.OrderDemandPO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 */
@Mapper
public interface OrderPlanConverter {

    OrderPlanConverter INSTANCE = Mappers.getMapper(OrderPlanConverter.class);

    OrderDemandPO toPO(OrderDemandBO reqBO);

    List<OrderDemandPO> toPOList(List<OrderDemandBO> reqBOList);


}
