package com.inventory.middle.infra.plan.persistence.mapper;

import com.inventory.middle.infra.plan.persistence.condition.demand.OrderDemandQueryCondition;
import com.inventory.middle.infra.plan.persistence.entity.OrderDemandPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderDemandMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderDemandPO record);

    int insertSelective(OrderDemandPO record);

    OrderDemandPO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderDemandPO record);

    int updateByPrimaryKey(OrderDemandPO record);

    int batchDelete(@Param("records") List<OrderDemandPO> oldOrderList);

    int batchInsert(@Param("records") List<OrderDemandPO> newOrderList);

    List<OrderDemandPO> queryByCondition(OrderDemandQueryCondition condition);
}
