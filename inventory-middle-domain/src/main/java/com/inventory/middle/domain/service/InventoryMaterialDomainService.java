package com.inventory.middle.domain.service;

import com.inventory.middle.client.material.dto.InventoryMaterialDTO;

import java.util.ArrayList;
import java.util.List;

/** 库存物料业务服务接口 */
public interface InventoryMaterialDomainService {
    ArrayList<InventoryMaterialDTO> listByMaterialCodeList(String tenantId, List<String> materialCodeList, List<String> outMaterialCodeList);
}