package com.inventory.middle.domain.service;

import com.inventory.middle.client.enums.ProductMasterDataSourceEnum;

/** 库存主数据来源业务服务接口（占位，C2 实现） */
public interface InventoryMasterDataSourceDomainService {
    ProductMasterDataSourceEnum getProductMasterDataSource(String tenantId);
}
