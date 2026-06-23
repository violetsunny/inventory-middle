package com.inventory.middle.client.dto.inventorysnapshot;

import com.inventory.middle.client.dto.BaseRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: TODO
 * @date 2021/8/31 19:38
 */
@Data
public class QueryCurrentInventoryReqDTO extends BaseRequest {

    @NotBlank(message = "batchNo 不能为空")
    private String batchNo;

    @NotBlank(message = "materialCode 不能为空")
    private String materialCode;

    @NotNull(message = "租户id不能为空")
    @Size(max = 40, message = "租户id过长，最大40")
    private String tenantId;

}