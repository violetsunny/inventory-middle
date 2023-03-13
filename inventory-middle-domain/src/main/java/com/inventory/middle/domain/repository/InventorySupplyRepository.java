package com.inventory.middle.domain.repository;

import com.inventory.middle.domain.model.entity.InventorySupply;
import com.inventory.middle.domain.model.types.InventorySupplyId;
import top.kdla.framework.dto.PageQuery;
import top.kdla.framework.dto.PageResponse;

import java.util.List;
import java.util.Map;

/**
 * 库存-供给Repository
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
public interface InventorySupplyRepository {

    /**
    * 分页查询
    *
    * @param pageQuery
    * @param params
    * @return
    */
    PageResponse<InventorySupply> queryPage(PageQuery pageQuery, Map<String, Object> params);

    /**
     * 通过ID获取库存-供给
     *
     * @param id
     * @return
     */
     InventorySupply findById(InventorySupplyId id);

    /**
     * 保存
     *
     * @param inventorysupply
     */
    boolean store(InventorySupply inventorysupply);

    /**
     * 更新
     *
     * @param inventorysupply
     */
    boolean update(InventorySupply inventorysupply);

    /**
     * 删除
     *
     * @param ids
     */
    boolean delete(List<InventorySupplyId> ids);
}
