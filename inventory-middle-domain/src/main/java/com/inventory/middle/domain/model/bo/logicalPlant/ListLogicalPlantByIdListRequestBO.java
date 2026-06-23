package com.inventory.middle.domain.model.bo.logicalPlant;

import lombok.Data;

import java.util.List;

@Data
public class ListLogicalPlantByIdListRequestBO {
    private String tenantId;

    private List<Long> idList;

    private List<String> noList;
}
