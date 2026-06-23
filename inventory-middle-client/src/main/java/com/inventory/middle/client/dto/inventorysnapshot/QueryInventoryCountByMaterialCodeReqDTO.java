package com.inventory.middle.client.dto.inventorysnapshot;

import com.inventory.middle.client.dto.BaseRequest;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class QueryInventoryCountByMaterialCodeReqDTO extends BaseRequest {

    @NotNull(message = "materialCodeList 不能为空")
    @Size(min = 1, message = "materialCodeList 物料编码至少一个")
    private List<String> materialCodeList;

    @NotNull(message = "租户id不能为空")
    @Size(max = 40, message = "租户id过长，最大40")
    private String tenantId;
}