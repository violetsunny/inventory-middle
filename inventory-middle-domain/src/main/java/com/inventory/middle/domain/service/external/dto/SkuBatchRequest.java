package com.inventory.middle.domain.service.external.dto;

import lombok.Data;
import java.io.Serializable;

/** SKU 批次同步请求 DTO */
@Data
public class SkuBatchRequest implements Serializable {
    private String skuCode;
    private String batchCode;
    private Integer batchNum;
    private String batchPrice;
    private String ext;
}
