package com.inventory.middle.client.material.service;


import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.SingleResponse;
import com.inventory.middle.client.material.dto.InventoryMaterialDTO;
import com.inventory.middle.client.material.dto.request.InventoryMaterialCreateRequest;
import com.inventory.middle.client.material.dto.request.InventoryMaterialPageRequest;
import com.inventory.middle.client.material.dto.request.InventoryMaterialUpdateRequest;
import com.inventory.middle.client.material.dto.request.ListMaterialCodeRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dongguo.tao
 * @description
 * @date 2022-05-06 09:10:29
 */
public interface InventoryMaterialService {

    /**
     * 批量新增物料
     * @param createRequestList
     * @return
     */
    SingleResponse<Boolean> batchCreate(List<InventoryMaterialCreateRequest> createRequestList);

    /**
     * 更新物料
     * @param updateRequest
     * @return
     */
    SingleResponse<Boolean> updateByMaterialCode(InventoryMaterialUpdateRequest updateRequest);

    /**
     * 根据编码查询物料信息
     * @param request
     * @return
     */
    SingleResponse<ArrayList<InventoryMaterialDTO>> listByMaterialCodeList(ListMaterialCodeRequest request);

    /**
     * 根据条件分页查询物料信息
     * @param pageRequest
     * @return
     */
    PageResponse<InventoryMaterialDTO> pageList(InventoryMaterialPageRequest pageRequest);
}
