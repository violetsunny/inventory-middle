package com.inventory.middle.client.dto.logicalPlant;

import com.inventory.middle.client.dto.base.Query;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 根据外部编码查询逻辑仓库请求
 * @author vincent.li
 * @date 2022-05-11
 */
@Data
public class LogicalPlantByOutQry extends Query {

    /**
     * 租户id
     */
    @NotBlank(message = "租户id不能为空")
    private String tenantId;

    /**
     * 外部逻辑仓编码==>逻辑仓的名称（精确查询）
     */
    @NotBlank(message = "外部逻辑仓编码不能为空")
    private String outLogicalPlantNo;

}
