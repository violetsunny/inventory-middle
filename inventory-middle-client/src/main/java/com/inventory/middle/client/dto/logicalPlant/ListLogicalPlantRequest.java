package com.inventory.middle.client.dto.logicalPlant;

import com.inventory.middle.client.dto.BaseRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 查询逻辑仓库请求
 */
@Data
public class ListLogicalPlantRequest extends BaseRequest {

    /**
     * 租户id
     */
    @NotBlank(message = "租户id不能为空")
    private String tenantId;
    /**
     * 公司code
     */
    private String companyCode;
    /**
     * 逻辑仓类型
     */
    private Integer type;
    /**
     * 逻辑仓的编号
     */
    private String logicalPlantNo;
    /**
     * 逻辑仓的名称
     */
    private String logicalPlantName;
    /**
     * 逻辑仓的名称（精确查询）
     */
    private String preciseLogicalPlantName;

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
