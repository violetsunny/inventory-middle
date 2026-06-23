package com.inventory.middle.domain.repository;

import com.inventory.middle.domain.model.entity.InventoryLog;
import java.util.List;

/**
 * 库存操作日志Repository接口
 */
public interface InventoryLogRepository {

    /**
     * 根据ID查询
     */
    InventoryLog findById(Long id);

    /**
     * 保存
     */
    boolean store(InventoryLog entity);

    /**
     * 更新
     */
    boolean update(InventoryLog entity);

    /**
     * 根据ID列表查询
     */
    List<InventoryLog> findByIds(List<Long> ids);
}
