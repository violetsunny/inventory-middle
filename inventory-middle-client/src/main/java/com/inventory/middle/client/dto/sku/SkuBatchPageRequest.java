package com.inventory.middle.client.dto.sku;

import lombok.Data;
import java.io.Serializable;

/** SKU批次分页查询请求（迁移自 com.enn.biz.dto.sku.skuBatch.req.SkuBatchPageRequest） */
@Data
public class SkuBatchPageRequest implements Serializable {
    private String skuCode;
    private Pager pager;
}
