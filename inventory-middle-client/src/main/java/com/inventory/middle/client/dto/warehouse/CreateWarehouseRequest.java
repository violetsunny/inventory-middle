package com.inventory.middle.client.dto.warehouse;

import com.inventory.middle.client.dto.BaseRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CreateWarehouseRequest extends BaseRequest {

    @NotBlank(message = "租户id不能为空")
    @Size(max = 40, message = "租户id过长，最大40")
    private String tenantId;

    @NotBlank(message = "物理仓库名称不能为空")
    @Size(max = 100, message = "仓库名称过长，最大100")
    private String warehouseName;

    @Size(max = 100, message = "负责人名称过长，最大100")
    private String ownerName;

    @Size(max = 30, message = "手机号码过长，最大30")
    private String phone;

    @Size(max = 100, message = "地址过长，最大100")
    private String address;

    @Size(max = 10, message = "省份代码过长，最大10")
    private String province;

    @Size(max = 16, message = "城市代码过长，最大16")
    private String city;

    @Size(max = 16, message = "区域代码过长，最大16")
    private String region;

    @Size(max = 200, message = "备注过长，最大200")
    private String remark;

    @NotBlank(message = "当前操作人员不能为空")
    @Size(max = 40, message = "操作人员过长，最大40")
    private String operator;

}
