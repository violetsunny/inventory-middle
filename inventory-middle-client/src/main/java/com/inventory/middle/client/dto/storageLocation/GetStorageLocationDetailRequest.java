package com.inventory.middle.client.dto.storageLocation;

import com.inventory.middle.client.dto.BaseRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class GetStorageLocationDetailRequest extends BaseRequest {

    @NotBlank(message = "租户id不能为空")
    private String tenantId;

    @NotNull(message = "存储地点id不能为空")
    private Long storageLocationId;

}
