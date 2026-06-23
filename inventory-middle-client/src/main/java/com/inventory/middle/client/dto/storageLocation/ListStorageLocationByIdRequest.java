package com.inventory.middle.client.dto.storageLocation;

import com.inventory.middle.client.dto.BaseRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author kll
 */
@Data
public class ListStorageLocationByIdRequest extends BaseRequest {
    @NotBlank(message = "租户id不能为空")
    private String tenantId;

    private List<Long> idList;

    private List<String> noList;
}
