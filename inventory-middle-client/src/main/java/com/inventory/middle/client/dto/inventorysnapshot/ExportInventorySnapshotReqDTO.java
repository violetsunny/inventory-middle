package com.inventory.middle.client.dto.inventorysnapshot;

import com.inventory.middle.client.dto.BaseRequest;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ExportInventorySnapshotReqDTO extends BaseRequest {

    private Long warehouseId;

    @NotNull(message = "逻辑仓id不能为空")
    private Long logicalPlantId;

    private Integer logicalPlantType;

    private String materialCode;

    private String materialCategoryCode;

    @NotNull(message = "租户id不能为空")
    @Size(max = 40, message = "租户id过长，最大40")
    private String tenantId;
}
