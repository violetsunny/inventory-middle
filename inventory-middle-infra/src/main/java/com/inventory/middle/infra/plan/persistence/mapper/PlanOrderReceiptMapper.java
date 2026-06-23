package com.inventory.middle.infra.plan.persistence.mapper;

import com.inventory.middle.infra.plan.persistence.entity.PlanOrderReceiptPO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PlanOrderReceiptMapper {

    int insertSelective(PlanOrderReceiptPO record);
}
