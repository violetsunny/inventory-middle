package com.inventory.middle.domain.repository;

import com.inventory.middle.domain.model.entity.MaterialDocItem;
import com.inventory.middle.domain.model.types.MaterialDocItemId;
import top.kdla.framework.dto.PageQuery;
import top.kdla.framework.dto.PageResponse;

import java.util.List;
import java.util.Map;

/**
 * 物料凭证-itemRepository
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
public interface MaterialDocItemRepository {

    /**
    * 分页查询
    *
    * @param pageQuery
    * @param params
    * @return
    */
    PageResponse<MaterialDocItem> queryPage(PageQuery pageQuery, Map<String, Object> params);

    /**
     * 通过ID获取物料凭证-item
     *
     * @param id
     * @return
     */
     MaterialDocItem findById(MaterialDocItemId id);

    /**
     * 保存
     *
     * @param materialdocitem
     */
    boolean store(MaterialDocItem materialdocitem);

    /**
     * 更新
     *
     * @param materialdocitem
     */
    boolean update(MaterialDocItem materialdocitem);

    /**
     * 删除
     *
     * @param ids
     */
    boolean delete(List<MaterialDocItemId> ids);
}
