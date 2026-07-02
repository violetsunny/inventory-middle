package com.inventory.middle.domain.repository;

import lombok.Data;

@Data
public class InventoryMaterialQueryParam {

    private String tenantId;

    private String materialCode;

    private String materialCodeFuzzy;

    private String outMaterialCode;

    private String outMaterialCodeFuzzy;

    private String materialNameFuzzy;

    private Integer deleted = 0;
}
