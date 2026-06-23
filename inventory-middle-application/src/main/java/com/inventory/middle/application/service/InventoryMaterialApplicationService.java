package com.inventory.middle.application.service;

import com.inventory.middle.client.material.dto.InventoryMaterialDTO;
import com.inventory.middle.client.material.dto.request.InventoryMaterialCreateRequest;
import com.inventory.middle.client.material.dto.request.InventoryMaterialPageRequest;
import com.inventory.middle.client.material.dto.request.InventoryMaterialUpdateRequest;
import com.inventory.middle.client.material.dto.request.ListMaterialCodeRequest;
import top.kdla.framework.dto.MultiResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.SingleResponse;

import java.util.List;
import java.util.Map;

/**
 * 库存物料ApplicationService
 */
public interface InventoryMaterialApplicationService {

    SingleResponse<Boolean> batchCreate(List<InventoryMaterialCreateRequest> createRequestList);

    SingleResponse<Boolean> updateByMaterialCode(InventoryMaterialUpdateRequest updateRequest);

    MultiResponse<InventoryMaterialDTO> listByMaterialCodeList(ListMaterialCodeRequest request);

    PageResponse<InventoryMaterialDTO> pageList(InventoryMaterialPageRequest pageRequest);

    /**
     * 按逻辑仓列表查询物料（plan 迁移：queryMaterialsByLogicalPlantNos）
     * 返回 Map<logicalPlantNo, List<materialCode or outMaterialCode>>
     *
     * @param logicalPlantNos 逻辑仓编码集合
     * @param tenantId        租户id
     * @return 各逻辑仓对应的外部物料编码列表（优先返回 outMaterialCode，无则返回 materialCode）
     */
    Map<String, List<String>> listByLogicalPlantNos(List<String> logicalPlantNos, String tenantId);

    /**
     * 按物料编码关键词模糊查询（plan 迁移：fuzzyQueryMaterialCode）
     *
     * @param keyword        物料编码关键词
     * @param logicalPlantNo 逻辑仓编码（可为 null 表示不限）
     * @param tenantId       租户id
     * @param maxSize        最大返回条数
     * @return 物料DTO列表
     */
    List<InventoryMaterialDTO> fuzzyQueryByMaterialCodeAndLogicalPlant(
            String keyword, String logicalPlantNo, String tenantId, int maxSize);
}
