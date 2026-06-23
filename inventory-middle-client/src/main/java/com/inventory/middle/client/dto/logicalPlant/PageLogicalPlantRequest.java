package com.inventory.middle.client.dto.logicalPlant;

import com.inventory.middle.client.dto.PageRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PageLogicalPlantRequest extends PageRequest {

    /**
     * 租户id
     */
    @NotBlank(message = "租户id不能为空")
    private String tenantId;
    /**
     * 逻辑仓编码
     */
    private String logicalPlantNo;

    /**
     * 逻辑仓名称
     */
    private String logicalPlantName;
    /**
     * 省代码
     */
    private String province;

    /**
     * 市代码
     */
    private String city;

    /**
     * 区代码
     */
    private String region;
}
