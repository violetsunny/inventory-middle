package com.inventory.middle.domain.repository;

import com.inventory.middle.domain.model.entity.Warehouse;
import com.inventory.middle.domain.model.types.WarehouseId;
import top.kdla.framework.dto.PageQuery;
import top.kdla.framework.dto.PageResponse;

import java.util.List;
import java.util.Map;

/**
 * 物理仓库表Repository
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
public interface WarehouseRepository {

    /**
    * 分页查询
    *
    * @param pageQuery
    * @param params
    * @return
    */
    PageResponse<Warehouse> queryPage(PageQuery pageQuery, Map<String, Object> params);

    /**
     * 通过ID获取物理仓库表
     *
     * @param id
     * @return
     */
     Warehouse findById(WarehouseId id);

    /**
     * 保存
     *
     * @param warehouse
     */
    boolean store(Warehouse warehouse);

    /**
     * 更新
     *
     * @param warehouse
     */
    boolean update(Warehouse warehouse);

    /**
     * 删除
     *
     * @param ids
     */
    boolean delete(List<WarehouseId> ids);
}
