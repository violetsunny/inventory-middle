package com.inventory.middle.client.dto.material;

import com.inventory.middle.client.dto.PageRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 分页查询  物料-逻辑仓  关系 的请求对象
 *
 * @author hjs
 * @date 2021/12/10
 */
@Data
public class PageQueryMaterialPlantRefRequest extends PageRequest {

    /**
     * 租户id
     */
    @NotBlank(message = "租户id不能为空")
    private String tenantId;
    /**
     * 逻辑仓编码
     */
    @NotBlank(message = "逻辑仓编码不能为空")
    private String logicalPlantNo;

    /**
     * 物料编码，模糊查询
     */
    private String fuzzyMaterialCode;
}
