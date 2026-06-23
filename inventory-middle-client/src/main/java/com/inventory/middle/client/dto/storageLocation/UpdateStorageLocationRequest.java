package com.inventory.middle.client.dto.storageLocation;

import com.inventory.middle.client.dto.BaseRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UpdateStorageLocationRequest extends BaseRequest {

    @NotBlank(message = "租户id不能为空")
    @Size(max = 40, message = "租户id过长，最大40")
    private String tenantId;

    @NotNull(message = "逻辑仓id不能为空")
    private Long logicalPlantId;

    @NotNull(message = "存储地点id不能为空")
    private Long storageLocationId;

    @NotNull(message = "存储地点描述不能为空")
    @Size(max = 200, message = "存储地点描述过长，最大200")
    private String description;

    @Size(max = 200, message = "存储地点位置过长，最大200")
    private String storageLocationPosition;

    @NotBlank(message = "当前操作人员不能为空")
    @Size(max = 40, message = "操作人员过长，最大40")
    private String operator;

}
