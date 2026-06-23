package com.inventory.middle.client.dto.logicalPlant;

import com.inventory.middle.client.dto.BaseRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 根据移动类型查询逻辑仓库请求
 */
@Data
public class ListLogicalPlantAdjustTypeRequest extends BaseRequest {

    /**
     * 租户id
     */
    @NotNull(message = "租户id不能为空")
    private String tenantId;
    /**
     * 公司code
     */
    private String companyCode;
    /**
     * 移动类型 eg:CG101
     */
    @NotBlank(message = "adjustType 移动类型不能为空")
    private String adjustType;

}
