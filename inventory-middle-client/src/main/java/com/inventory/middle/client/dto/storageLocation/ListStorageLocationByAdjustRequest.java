package com.inventory.middle.client.dto.storageLocation;

import com.inventory.middle.client.dto.BaseRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 根据移动类型查询
 * @author kll
 */
@Data
public class ListStorageLocationByAdjustRequest extends BaseRequest {
    @NotBlank(message = "租户id不能为空")
    private String tenantId;
    @NotBlank(message = "逻辑仓No不能为空")
    private String logicalPlantNo;
    /**
     * 移动类型，不传是普通库存地点，传会根据移动类型获取特殊库存地点
     */
    private String adjustType;
    /**
     * 库存地点类型 StorageLocationTypeEnum
     */
    private Integer type;
}
