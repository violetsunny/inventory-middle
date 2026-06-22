package com.inventory.middle.infra.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.inventory.middle.domain.model.bo.inventory.InventorySupplyByDayQueryBO;
import com.inventory.middle.domain.model.bo.inventory.InventorySupplyByDayRespBO;
import com.inventory.middle.infra.persistence.entity.InventorySupplyDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 库存-供给Mapper
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 20:32:10
 */
@Mapper
public interface InventorySupplyMapper extends BaseMapper<InventorySupplyDo> {

    IPage<InventorySupplyDo> queryPage(IPage<InventorySupplyDo> page, @Param("params") Map<String, Object> params);

    InventorySupplyDo queryEntity(@Param("params") Map<String, Object> params);

    List<InventorySupplyDo> queryList(@Param("params") Map<String, Object> params);

    InventorySupplyDo findById(@Param("id") Long id);

    /** 按天聚合供给数量（plan迁移：querySupplyInventory / queryOverdueSupplyInventory） */
    List<InventorySupplyByDayRespBO> queryInventorySupplyByDay(InventorySupplyByDayQueryBO query);
}
