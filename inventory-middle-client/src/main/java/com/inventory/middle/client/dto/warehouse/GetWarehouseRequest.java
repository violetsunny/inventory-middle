package com.inventory.middle.client.dto.warehouse;

import com.inventory.middle.client.dto.BaseRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class GetWarehouseRequest extends BaseRequest {

    @NotBlank(message = "租户id不能为空")
    private String tenantId;

    @NotNull(message = "物理仓库id不能为空")
    private Long warehouseId;

}
