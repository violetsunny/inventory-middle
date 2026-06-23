package com.inventory.middle.client.dto.warehouse;

import com.inventory.middle.client.dto.PageRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class PageWarehouseRequest extends PageRequest {

    @NotBlank(message = "租户id不能为空")
    @Size(max = 40, message = "租户id过长，最大40")
    private String tenantId;

    private String warehouseNo;

    private String warehouseName;

}
