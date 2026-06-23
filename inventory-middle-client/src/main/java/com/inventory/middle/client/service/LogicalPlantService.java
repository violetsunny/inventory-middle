package com.inventory.middle.client.service;

import com.inventory.middle.client.dto.EnumResponse;
import com.inventory.middle.client.dto.logicalPlant.*;
// RDFA import removed;
// RDFA import removed;

import java.util.ArrayList;

/**
 * 查询逻辑仓
 * @author kll
 * @version data
 */
public interface LogicalPlantService {

    /**
     * 查询逻辑仓的类型枚举列表
     * @return
     */
    top.kdla.framework.dto.PageResponse<EnumResponse<Integer>> types();

    /**
     * 分页查询逻辑仓库
     * @param request
     * @return
     */
    top.kdla.framework.dto.PageResponse<PageLogicalPlantResponse> page(PageLogicalPlantRequest request);

    /**
     * 查询逻辑仓库列表
     * @param request
     * @return
     */
    top.kdla.framework.dto.PageResponse<LogicalPlantResponse> list(ListLogicalPlantRequest request);

    /**
     * 批量查询逻辑仓库
     * @param request
     * @return
     */
    top.kdla.framework.dto.PageResponse<LogicalPlantResponse> listByIdList(ListLogicalPlantByIdListRequest request);

    /**
     * 创建逻辑仓库
     * @param request
     * @return
     */
    top.kdla.framework.dto.SingleResponse<Long> create(CreateLogicalPlantRequest request);

    /**
     * 创建逻辑仓库
     * @param request
     * @return
     */
    top.kdla.framework.dto.SingleResponse<CreateLogicalPlantResponse> createV2(CreateLogicalPlantRequest request);

    /**
     * 更新逻辑仓库
     * @param request
     * @return
     */
    top.kdla.framework.dto.SingleResponse<Void> update(UpdateLogicalPlantRequest request);

    /**
     * 查询单个逻辑仓库的详情
     * @param request
     * @return
     */
    top.kdla.framework.dto.SingleResponse<GetLogicalPlantDetailResponse> detail(GetLogicalPlantDetailRequest request);

    /**
     * 根据外部编码查询逻辑仓库信息
     * @param qry
     * @return
     */
    top.kdla.framework.dto.SingleResponse<LogicalPlantByOutResp> queryByOutNo(LogicalPlantByOutQry qry);

}
