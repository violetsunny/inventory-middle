package com.inventory.middle.domain.repository;

import com.inventory.middle.domain.model.entity.InventoryMapHis;
import com.inventory.middle.domain.model.types.InventoryMapHisId;
import top.kdla.framework.dto.PageQuery;
import top.kdla.framework.dto.PageResponse;

import java.util.List;
import java.util.Map;

/**
 * 移动平均价历史记录Repository
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
public interface InventoryMapHisRepository {

    /**
    * 分页查询
    *
    * @param pageQuery
    * @param params
    * @return
    */
    PageResponse<InventoryMapHis> queryPage(PageQuery pageQuery, Map<String, Object> params);

    /**
     * 通过ID获取移动平均价历史记录
     *
     * @param id
     * @return
     */
     InventoryMapHis findById(InventoryMapHisId id);

    /**
     * 保存
     *
     * @param inventorymaphis
     */
    boolean store(InventoryMapHis inventorymaphis);

    /**
     * 更新
     *
     * @param inventorymaphis
     */
    boolean update(InventoryMapHis inventorymaphis);

    /**
     * 删除
     *
     * @param ids
     */
    boolean delete(List<InventoryMapHisId> ids);
}
