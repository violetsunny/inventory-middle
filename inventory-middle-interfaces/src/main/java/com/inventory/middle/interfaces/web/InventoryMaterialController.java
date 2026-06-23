package com.inventory.middle.interfaces.web;

import com.inventory.middle.application.service.InventoryMaterialApplicationService;
import com.inventory.middle.client.material.dto.InventoryMaterialDTO;
import com.inventory.middle.client.material.dto.request.InventoryMaterialCreateRequest;
import com.inventory.middle.client.material.dto.request.InventoryMaterialPageRequest;
import com.inventory.middle.client.material.dto.request.InventoryMaterialUpdateRequest;
import com.inventory.middle.client.material.dto.request.ListMaterialCodeRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import top.kdla.framework.dto.MultiResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.log.catchlog.CatchAndLog;

import javax.annotation.Resource;
import java.util.List;

/**
 * 库存物料Controller
 * 迁移自: InventoryMaterialService (Dubbo Facade)
 */
@Tag(name = "库存物料管理")
@RestController
@RequestMapping("/inventory-material")
@Slf4j
@CatchAndLog
public class InventoryMaterialController {

    @Resource
    private InventoryMaterialApplicationService inventoryMaterialApplicationService;

    @Operation(summary = "批量新增物料")
    @PostMapping("/batch-create")
    public SingleResponse<Boolean> batchCreate(@RequestBody List<InventoryMaterialCreateRequest> createRequestList) {
        return inventoryMaterialApplicationService.batchCreate(createRequestList);
    }

    @Operation(summary = "更新物料")
    @PostMapping("/update")
    public SingleResponse<Boolean> updateByMaterialCode(@RequestBody InventoryMaterialUpdateRequest updateRequest) {
        return inventoryMaterialApplicationService.updateByMaterialCode(updateRequest);
    }

    @Operation(summary = "根据编码查询物料信息")
    @PostMapping("/list-by-codes")
    public MultiResponse<InventoryMaterialDTO> listByMaterialCodeList(@RequestBody ListMaterialCodeRequest request) {
        return inventoryMaterialApplicationService.listByMaterialCodeList(request);
    }

    @Operation(summary = "分页查询物料信息")
    @PostMapping("/page")
    public PageResponse<InventoryMaterialDTO> pageList(@RequestBody InventoryMaterialPageRequest pageRequest) {
        return inventoryMaterialApplicationService.pageList(pageRequest);
    }

    @Operation(summary = "查询城燃物料数据（BFF /inventory-material/queryCityGasMaterialPage）")
    @PostMapping("/queryCityGasMaterialPage")
    public PageResponse<InventoryMaterialDTO> queryCityGasMaterialPage(@RequestBody InventoryMaterialPageRequest pageRequest) {
        // 城燃专用分页查询，复用通用 pageList（如有城燃特殊过滤条件待补充）
        return inventoryMaterialApplicationService.pageList(pageRequest);
    }
}
