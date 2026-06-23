package com.inventory.middle.client.service;

import com.inventory.middle.client.dto.warehouse.*;
// RDFA import removed;
// RDFA import removed;

/**
 * 物理仓库服务
 * @author hjs
 * @version 1.0.0
 */
public interface WarehouseService {

    /**
     * 创建物理仓库
     * @param request
     * @return
     */
    top.kdla.framework.dto.SingleResponse<Long> create(CreateWarehouseRequest request);

    /**
     * 更新物理仓库
     * @param request
     * @return
     */
    top.kdla.framework.dto.SingleResponse<Void> update(UpdateWarehouseRequest request);

    /**
     * 分页查询物理仓库
     * @param request
     * @return
     */
    top.kdla.framework.dto.PageResponse<WarehouseResponse> pageList(PageWarehouseRequest request);

    /**
     * 查询物理仓库列表
     * @param request
     * @return
     */
    top.kdla.framework.dto.PageResponse<WarehouseResponse> list(ListWarehouseRequest request);

    /**
     * 查询单个物理仓库详情
     * @param request
     * @return
     */
    top.kdla.framework.dto.SingleResponse<GetWarehouseDetailResponse> detail(GetWarehouseRequest request);
}
