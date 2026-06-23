package com.inventory.middle.domain.repository;

import com.inventory.middle.domain.model.entity.MaterialLogicalPlantRef;
import java.util.List;

/**
 * 物料逻辑仓关联Repository接口
 */
public interface MaterialLogicalPlantRefRepository {

    /**
     * 根据ID查询
     */
    MaterialLogicalPlantRef findById(Long id);

    /**
     * 保存
     */
    boolean store(MaterialLogicalPlantRef entity);

    /**
     * 更新
     */
    boolean update(MaterialLogicalPlantRef entity);

    /**
     * 根据ID列表查询
     */
    List<MaterialLogicalPlantRef> findByIds(List<Long> ids);
}
