package com.inventory.middle.domain.repository;

import com.inventory.middle.domain.model.entity.ShipmentLine;
import com.inventory.middle.domain.model.types.ShipmentLineId;
import top.kdla.framework.dto.PageQuery;
import top.kdla.framework.dto.PageResponse;

import java.util.List;
import java.util.Map;

/**
 * 交运单明细Repository
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:33
 */
public interface ShipmentLineRepository {

    /**
    * 分页查询
    *
    * @param pageQuery
    * @param params
    * @return
    */
    PageResponse<ShipmentLine> queryPage(PageQuery pageQuery, Map<String, Object> params);

    /**
     * 通过ID获取交运单明细
     *
     * @param id
     * @return
     */
     ShipmentLine findById(ShipmentLineId id);

    /**
     * 保存
     *
     * @param shipmentline
     */
    boolean store(ShipmentLine shipmentline);

    /**
     * 更新
     *
     * @param shipmentline
     */
    boolean update(ShipmentLine shipmentline);

    /**
     * 删除
     *
     * @param ids
     */
    boolean delete(List<ShipmentLineId> ids);
}
