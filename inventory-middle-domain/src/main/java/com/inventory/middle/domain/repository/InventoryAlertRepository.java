package com.inventory.middle.domain.repository;

import com.inventory.middle.domain.model.entity.InventoryAlert;
import com.inventory.middle.domain.model.types.InventoryAlertId;
import top.kdla.framework.dto.PageQuery;
import top.kdla.framework.dto.PageResponse;

import java.util.List;
import java.util.Map;

/**
 * 库存报警日志Repository
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:29
 */
public interface InventoryAlertRepository {

    /**
    * 分页查询
    *
    * @param pageQuery
    * @param params
    * @return
    */
    PageResponse<InventoryAlert> queryPage(PageQuery pageQuery, Map<String, Object> params);

    /**
     * 通过ID获取库存报警日志
     *
     * @param id
     * @return
     */
     InventoryAlert findById(InventoryAlertId id);

    /**
     * 保存
     *
     * @param inventoryalert
     */
    boolean store(InventoryAlert inventoryalert);

    /**
     * 更新
     *
     * @param inventoryalert
     */
    boolean update(InventoryAlert inventoryalert);

    /**
     * 删除
     *
     * @param ids
     */
    boolean delete(List<InventoryAlertId> ids);
}
