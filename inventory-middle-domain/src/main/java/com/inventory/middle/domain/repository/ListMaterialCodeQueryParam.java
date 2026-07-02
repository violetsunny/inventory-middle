package com.inventory.middle.domain.repository;

import lombok.Data;

import java.util.List;

@Data
public class ListMaterialCodeQueryParam {

    private String tenantId;

    private List<String> materialCodeList;

    private List<String> outMaterialCodeList;

    private Integer deleted = 0;
}
