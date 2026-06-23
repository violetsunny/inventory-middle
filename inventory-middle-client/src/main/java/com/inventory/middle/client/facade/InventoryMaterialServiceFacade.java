package com.inventory.middle.client.facade;

import com.inventory.middle.client.material.dto.InventoryMaterialDTO;
import com.inventory.middle.client.material.dto.request.InventoryMaterialCreateRequest;
import com.inventory.middle.client.material.dto.request.InventoryMaterialPageRequest;
import com.inventory.middle.client.material.dto.request.InventoryMaterialUpdateRequest;
import com.inventory.middle.client.material.dto.request.ListMaterialCodeRequest;
import top.kdla.framework.dto.MultiResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.SingleResponse;

import java.util.List;

/**
 * 库存物料Facade接口
 */
public interface InventoryMaterialServiceFacade {

    /** 批量新增物料 */
    SingleResponse<Boolean> batchCreate(List<InventoryMaterialCreateRequest> createRequestList);

    /** 更新物料 */
    SingleResponse<Boolean> updateByMaterialCode(InventoryMaterialUpdateRequest updateRequest);

    /** 根据编码查询物料信息 */
    MultiResponse<InventoryMaterialDTO> listByMaterialCodeList(ListMaterialCodeRequest request);

    /** 分页查询物料信息 */
    PageResponse<InventoryMaterialDTO> pageList(InventoryMaterialPageRequest pageRequest);
}
