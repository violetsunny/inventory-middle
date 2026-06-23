package com.inventory.middle.infra.persistence.mapper;

import com.inventory.middle.infra.persistence.entity.InventoryLogDo;
import com.inventory.middle.infra.persistence.entity.InventoryLogQueryDo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description 库存日志
 * @author dongguo.tao
 * @date 2021-12-14 14:30:33
 */
@Mapper
public interface InventoryLogMapper {

    /**
     * 新增
     * @param inventoryLogPO
     * @return
     **/
    int insert(InventoryLogDo inventoryLogPO);

    /**
     * 批量插入
     * @param list
     * @return
     */
    int insertBatch(List<InventoryLogDo> list);

    /**
     * 批量查询
     * @param queryPO
     * @return
     */
    List<InventoryLogDo> list(InventoryLogQueryDo queryPO);

}
