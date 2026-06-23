package com.inventory.middle.domain.model.bo.warehouse;

import lombok.Data;

import java.util.List;

@Data
public class ListWarehouseRequestBO {

    private String tenantId;
    /**
     * 物理仓类型(1-真实,0-虚拟)
     */
    private Integer warehouseType;

    private List<Long> idList;

    private List<String> noList;

}
