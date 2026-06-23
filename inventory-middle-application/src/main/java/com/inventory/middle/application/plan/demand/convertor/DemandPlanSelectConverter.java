package com.inventory.middle.application.plan.demand.convertor;

import com.inventory.middle.application.plan.demand.bo.DemandPlanSelectResBO;
import com.inventory.middle.infra.plan.persistence.entity.DemandPlanPO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @description 将得到的po分页查询结果转换成bo分页查询结果
 * @author zhouxinzhong
 * @date 2021/9/29 12:51
 */
@Mapper
public interface DemandPlanSelectConverter {

    DemandPlanSelectConverter INSTANCE = Mappers.getMapper(DemandPlanSelectConverter.class);

    List<DemandPlanSelectResBO> toBOList(List<DemandPlanPO> POList);
}
