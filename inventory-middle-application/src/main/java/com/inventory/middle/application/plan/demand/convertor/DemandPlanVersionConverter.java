package com.inventory.middle.application.plan.demand.convertor;

import com.inventory.middle.application.plan.demand.bo.DemandPlanVersionBO;
import com.inventory.middle.infra.plan.persistence.entity.DemandPlanPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author zhouxinzhong
 * @date 2021/9/29 12:51
 */
@Mapper
public interface DemandPlanVersionConverter {

    DemandPlanVersionConverter INSTANCE = Mappers.getMapper(DemandPlanVersionConverter.class);

    @Mapping(source = "po.id", target = "demandPlanId")
    DemandPlanVersionBO toBO(DemandPlanPO po);

    List<DemandPlanVersionBO> toBOList(List<DemandPlanPO> POList);
}
