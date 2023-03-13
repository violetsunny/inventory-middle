package com.inventory.middle.domain.repository;

import com.inventory.middle.domain.model.entity.InventoryMap;
import com.inventory.middle.domain.model.types.InventoryMapId;
import top.kdla.framework.dto.PageQuery;
import top.kdla.framework.dto.PageResponse;

import java.util.List;
import java.util.Map;

/**
 * 移动平均价Repository
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
public interface InventoryMapRepository {

    /**
    * 分页查询
    *
    * @param pageQuery
    * @param params
    * @return
    */
    PageResponse<InventoryMap> queryPage(PageQuery pageQuery, Map<String, Object> params);

    /**
     * 通过ID获取移动平均价
     *
     * @param id
     * @return
     */
     InventoryMap findById(InventoryMapId id);

    /**
     * 保存
     *
     * @param inventorymap
     */
    boolean store(InventoryMap inventorymap);

    /**
     * 更新
     *
     * @param inventorymap
     */
    boolean update(InventoryMap inventorymap);

    /**
     * 删除
     *
     * @param ids
     */
    boolean delete(List<InventoryMapId> ids);
}
