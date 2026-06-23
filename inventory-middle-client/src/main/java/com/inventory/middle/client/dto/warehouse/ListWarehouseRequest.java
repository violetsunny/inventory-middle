package com.inventory.middle.client.dto.warehouse;

import com.inventory.middle.client.dto.BaseRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class ListWarehouseRequest extends BaseRequest {

    @NotBlank(message = "租户id不能为空")
    private String tenantId;

    private List<Long> idList;

    private List<String> noList;
}
