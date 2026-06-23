package com.inventory.middle.client.service;

import com.inventory.middle.client.dto.EnumResponse;
import com.inventory.middle.client.dto.storageLocation.*;
// RDFA import removed;
// RDFA import removed;

import java.util.ArrayList;

/**
 * 存储地点服务
 * @author hjs
 * @version 1.0.0
 */
public interface StorageLocationService {

    /**
     * 创建存储地点
     * @param request
     * @return
     */
    top.kdla.framework.dto.SingleResponse<Long> create(CreateStorageLocationRequest request);

    /**
     * 更新存储地点
     * @param request
     * @return
     */
    top.kdla.framework.dto.SingleResponse<Void> update(UpdateStorageLocationRequest request);

    /**
     * 查询存储地点列表
     * @param request
     * @return
     */
    top.kdla.framework.dto.PageResponse<StorageLocationResponse> list(ListStorageLocationRequest request);

    /**
     * 批量查询存储地点列表
     * @param request
     * @return
     */
    top.kdla.framework.dto.SingleResponse<ArrayList<StorageLocationResponse>> listBy(ListStorageLocationByIdRequest request);

    /**
     * 根据移动类型查询存储地点列表
     * @param request
     * @return
     */
    top.kdla.framework.dto.SingleResponse<ArrayList<StorageLocationResponse>> listByAdjust(ListStorageLocationByAdjustRequest request);

    /**
     * 查询单个存储地点的详情
     * @param request
     * @return
     */
    top.kdla.framework.dto.SingleResponse<StorageLocationResponse> detail(GetStorageLocationDetailRequest request);

    /**
     * 查询存储地点类型
     * @return
     */
    top.kdla.framework.dto.PageResponse<EnumResponse<Integer>> queryStorageLocationType();
}
