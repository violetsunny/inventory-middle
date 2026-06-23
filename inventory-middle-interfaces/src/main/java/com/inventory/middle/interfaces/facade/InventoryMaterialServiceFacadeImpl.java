package com.inventory.middle.interfaces.facade;

import com.inventory.middle.application.service.InventoryMaterialApplicationService;
import com.inventory.middle.client.facade.InventoryMaterialServiceFacade;
import com.inventory.middle.client.material.dto.InventoryMaterialDTO;
import com.inventory.middle.client.material.dto.request.InventoryMaterialCreateRequest;
import com.inventory.middle.client.material.dto.request.InventoryMaterialPageRequest;
import com.inventory.middle.client.material.dto.request.InventoryMaterialUpdateRequest;
import com.inventory.middle.client.material.dto.request.ListMaterialCodeRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.kdla.framework.dto.MultiResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.log.catchlog.CatchAndLog;

import javax.annotation.Resource;
import java.util.List;

/**
 * 库存物料FacadeImpl
 */
@Component
@Slf4j
@CatchAndLog
public class InventoryMaterialServiceFacadeImpl implements InventoryMaterialServiceFacade {

    @Resource
    private InventoryMaterialApplicationService inventoryMaterialApplicationService;

    @Override
    public SingleResponse<Boolean> batchCreate(List<InventoryMaterialCreateRequest> createRequestList) {
        return inventoryMaterialApplicationService.batchCreate(createRequestList);
    }

    @Override
    public SingleResponse<Boolean> updateByMaterialCode(InventoryMaterialUpdateRequest updateRequest) {
        return inventoryMaterialApplicationService.updateByMaterialCode(updateRequest);
    }

    @Override
    public MultiResponse<InventoryMaterialDTO> listByMaterialCodeList(ListMaterialCodeRequest request) {
        return inventoryMaterialApplicationService.listByMaterialCodeList(request);
    }

    @Override
    public PageResponse<InventoryMaterialDTO> pageList(InventoryMaterialPageRequest pageRequest) {
        return inventoryMaterialApplicationService.pageList(pageRequest);
    }
}
