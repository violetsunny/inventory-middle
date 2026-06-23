package com.inventory.middle.client.dto.storageLocation;

import com.inventory.middle.client.dto.BaseRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ListStorageLocationRequest extends BaseRequest {


    @NotBlank(message = "租户id不能为空")
    private String tenantId;

    @NotNull(message = "逻辑仓id不能为空")
    private Long logicalPlantId;

    /**
     * 存储地点类型
     */
    private Integer locationType;
    /**
     * 存储地点描述（精确查询）
     */
    private String preciseDescription;

}
