package com.inventory.middle.client.dto.sparepart;

import com.inventory.middle.client.dto.BaseRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PageSparePartReqDTO extends BaseRequest {

    @NotBlank(message = "租户id不能为空")
    String tenantId;

    @NotBlank(message = "物料编码不能为空")
    String materialCode;

    Integer pageNum = 1;

    Integer pageSize = 10;

}
