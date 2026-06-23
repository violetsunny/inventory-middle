package com.inventory.middle.client.dto.inventorysnapshot;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.inventory.middle.client.dto.BaseRequest;

import lombok.Data;

/**
 * @author dongguo.tao
 * @version 1.0
 * @description:
 * @date 2021-10-25 16:04:41
 */
@Data
public class ListQueryInventorySnapshotReqDTO extends BaseRequest {

    @Size(min = 1,message = "逻辑仓编码至少一个")
    private List<String> logicalPlantNoList;

    @Size(min = 1,message = "物料编码至少一个")
    private List<String> materialCodeList;

    @NotNull(message = "租户id不能为空")
    @Size(max = 32, message = "租户id过长，最大32")
    private String tenantId;
}