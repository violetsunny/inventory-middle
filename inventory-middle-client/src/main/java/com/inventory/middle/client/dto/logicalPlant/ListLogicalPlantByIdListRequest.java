package com.inventory.middle.client.dto.logicalPlant;

import com.inventory.middle.client.dto.BaseRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class ListLogicalPlantByIdListRequest extends BaseRequest {

    @NotBlank(message = "租户id不能为空")
    private String tenantId;

    private List<Long> idList;

    private List<String> noList;
}
