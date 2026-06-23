package com.inventory.middle.client.dto.inventorysnapshot;

import com.inventory.middle.client.dto.BaseRequest;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @description 库存
 * @author peisheng.wang
 * @date 2021-06-16
 */
@Data
public class PagedInventorySnapshotReqDTO extends BaseRequest {


    private Long warehouseId;

    private Long logicalPlantId;

    private String logicalPlantNo;

    private Integer logicalPlantType;

    private String materialCode;

    private String materialCategoryCode;

    private Integer warning;

    @NotNull(message = "pageSize 不能为空")
    private Integer pageSize;

    @NotNull(message = "pageNum 不能为空")
    private Integer pageNum;

    @NotNull(message = "租户id不能为空")
    @Size(max = 40, message = "租户id过长，最大40")
    private String tenantId;
}
