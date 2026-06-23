package com.inventory.middle.domain.service.external.dto;

import lombok.Data;
import java.io.Serializable;

/** SKU 批次查询响应 DTO */
@Data
public class SkuBatchResponse implements Serializable {
    private String skuCode;
    private String batchCode;
    private String ext;
}
