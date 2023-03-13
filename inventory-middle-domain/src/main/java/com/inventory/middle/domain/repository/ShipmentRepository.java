package com.inventory.middle.domain.repository;

import com.inventory.middle.domain.model.entity.Shipment;
import com.inventory.middle.domain.model.types.ShipmentId;
import top.kdla.framework.dto.PageQuery;
import top.kdla.framework.dto.PageResponse;

import java.util.List;
import java.util.Map;

/**
 * 交运单Repository
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
public interface ShipmentRepository {

    /**
    * 分页查询
    *
    * @param pageQuery
    * @param params
    * @return
    */
    PageResponse<Shipment> queryPage(PageQuery pageQuery, Map<String, Object> params);

    /**
     * 通过ID获取交运单
     *
     * @param id
     * @return
     */
     Shipment findById(ShipmentId id);

    /**
     * 保存
     *
     * @param shipment
     */
    boolean store(Shipment shipment);

    /**
     * 更新
     *
     * @param shipment
     */
    boolean update(Shipment shipment);

    /**
     * 删除
     *
     * @param ids
     */
    boolean delete(List<ShipmentId> ids);
}
