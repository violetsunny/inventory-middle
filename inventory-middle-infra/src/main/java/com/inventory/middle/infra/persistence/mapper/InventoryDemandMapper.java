package com.inventory.middle.infra.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.inventory.middle.domain.model.bo.inventory.InventoryDemandByDayQueryBO;
import com.inventory.middle.domain.model.bo.inventory.InventoryDemandByDayRespBO;
import com.inventory.middle.infra.persistence.entity.InventoryDemandDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 库存-需求Mapper
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 20:32:11
 */
@Mapper
public interface InventoryDemandMapper extends BaseMapper<InventoryDemandDo> {

    IPage<InventoryDemandDo> queryPage(IPage<InventoryDemandDo> page, @Param("params") Map<String, Object> params);

    InventoryDemandDo queryEntity(@Param("params") Map<String, Object> params);

    List<InventoryDemandDo> queryList(@Param("params") Map<String, Object> params);

    InventoryDemandDo findById(@Param("id") Long id);

    /** 按天聚合需求数量（plan迁移：queryDemandInventory / queryOverdueDemandInventory） */
    List<InventoryDemandByDayRespBO> queryInventoryDemandByDay(InventoryDemandByDayQueryBO query);
}
