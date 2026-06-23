package com.inventory.middle.infra.persistence.entity;

import lombok.Data;
import java.util.List;

/** SKU 批次查询参数 PO */
@Data
public class SkuBatchQueryPO {
    private String skuCode;
    private String tenantId;
    private List<String> skuCodeList;
    private Integer pageNum;
    private Integer pageSize;
}
