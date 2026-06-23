package com.inventory.middle.client.dto.logicalPlant;

import com.inventory.middle.client.dto.BaseRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class GetLogicalPlantDetailRequest extends BaseRequest {

    @NotBlank(message = "租户id不能为空")
    private String tenantId;

    private Long logicalPlantId;

    private String logicalPlantNo;

}
