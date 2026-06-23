package com.inventory.middle.domain.service.external.dto;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

/** SKU 响应 DTO */
@Data
public class SkuResponse implements Serializable {
    private Long id;
    private Long spuId;
    private Long categoryId;
    private String code;
    private String type;
    private String name;
    private String aliasName;
    private Long unitId;
    private String unit;
    private String unitName;
    private String description;
    private String status;
    private String productType;
    private String materialCategoryCode;
}

