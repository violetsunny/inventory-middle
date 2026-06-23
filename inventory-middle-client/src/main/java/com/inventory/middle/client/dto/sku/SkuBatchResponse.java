package com.inventory.middle.client.dto.sku;

import lombok.Data;
import java.io.Serializable;

/** SKU批次查询响应（迁移自 com.enn.biz.dto.sku.skuBatch.res.SkuBatchResponse） */
@Data
public class SkuBatchResponse implements Serializable {
    private String skuCode;
    private String batchCode;
    private String ext;
    private String tenantId;
    private String updatorId;
}
