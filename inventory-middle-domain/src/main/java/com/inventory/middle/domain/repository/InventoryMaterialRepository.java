package com.inventory.middle.domain.repository;

import com.inventory.middle.domain.model.entity.InventoryMaterial;
import java.util.List;

/**
 * 库存物料Repository接口
 */
public interface InventoryMaterialRepository {

    /**
     * 根据ID查询
     */
    InventoryMaterial findById(Long id);

    /**
     * 保存
     */
    boolean store(InventoryMaterial entity);

    /**
     * 更新
     */
    boolean update(InventoryMaterial entity);

    /**
     * 根据ID列表查询
     */
    List<InventoryMaterial> findByIds(List<Long> ids);

    /**
     * 按条件查询
     */
    List<InventoryMaterial> listByCondition(InventoryMaterialQueryParam queryParam);

    /**
     * 按物料编码列表查询
     */
    List<InventoryMaterial> listByMaterialCodes(ListMaterialCodeQueryParam queryParam);

}
