package com.inventory.middle.client.dto.logicalPlant;

import com.inventory.middle.client.dto.BaseRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UpdateLogicalPlantRequest extends BaseRequest {

    @NotBlank(message = "租户id不能为空")
    @Size(max = 40, message = "租户id过长，最大40")
    private String tenantId;

    @NotNull(message = "逻辑仓id不能为空")
    private Long logicalPlantId;

    @NotBlank(message = "逻辑仓名称不能为空")
    @Size(max = 100, message = "逻辑仓库名称过长，最大100")
    private String logicalPlantName;

    @Size(max = 10, message = "省份代码过长，最大10")
    private String province;

    @Size(max = 16, message = "城市代码过长，最大16")
    private String city;

    @Size(max = 16, message = "区域代码过长，最大16")
    private String region;

    @Size(max = 200, message = "备注过长，最大200")
    private String address;

    @NotBlank(message = "操作人不能为空")
    @Size(max = 40, message = "用户过长，最大40")
    private String operator;

}
