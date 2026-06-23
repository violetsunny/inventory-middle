package com.inventory.middle.domain.service.external.dto;

import lombok.Data;
import java.io.Serializable;

/** SKU 批次分页查询请求 DTO */
@Data
public class SkuBatchPageRequest implements Serializable {
    private String skuCode;
    private String batchCode;
    private Integer pageNum;
    private Integer pageSize;
}
