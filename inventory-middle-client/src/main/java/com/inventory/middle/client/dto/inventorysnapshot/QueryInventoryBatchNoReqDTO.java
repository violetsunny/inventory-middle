package com.inventory.middle.client.dto.inventorysnapshot;

import com.inventory.middle.client.dto.BaseRequest;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description:
 * @date 2021/8/30 9:04
 */
@Data
public class QueryInventoryBatchNoReqDTO extends BaseRequest {

    @NotNull(message = "batchNoList 不能为空")
    @Size(min = 1, message = "batchNoList 批次号至少一个")
    private List<String> batchNoList;

    @NotNull(message = "pageSize 不能为空")
    private Integer pageSize;

    @NotNull(message = "pageNum 不能为空")
    private Integer pageNum;

    @NotNull(message = "租户id不能为空")
    @Size(max = 40, message = "租户id过长，最大40")
    private String tenantId;
}