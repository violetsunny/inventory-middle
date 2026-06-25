package com.inventory.middle.application.service;

import top.kdla.framework.dto.SingleResponse;

/**
 * 外部产品中心调用 ApplicationService（M15：下沉 MaterialDocController / InventoryTransitController 的直连依赖）
 */
public interface InventoryExternalApplicationService {

    /** 查询组装物料信息 */
    SingleResponse<Object> queryBuildMaterialInfo(String skuCode, String tenantId);

    /** 按名称模糊查询物料信息（分页） */
    SingleResponse<Object> fuzzyQueryByName(String skuName, int pageNum, int pageSize, String tenantId);

    /** 根据单位编码查询单位名称 */
    String getUnitNameByCode(String uomCode, String tenantId);
}
