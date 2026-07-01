package com.inventory.middle.domain.service.external;

import com.inventory.middle.client.material.dto.InventoryMaterialDTO;

import java.util.ArrayList;
import java.util.List;

/** 库存物料远程服务接口 */
public interface RemoteInventoryMaterialService {
    ArrayList<InventoryMaterialDTO> listByMaterialCodeList(String tenantId, List<String> materialCodeList, List<String> outMaterialCodeList);
}
