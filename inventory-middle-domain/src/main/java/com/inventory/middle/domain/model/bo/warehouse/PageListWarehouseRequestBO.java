package com.inventory.middle.domain.model.bo.warehouse;

import lombok.Data;

@Data
public class PageListWarehouseRequestBO {

    private String tenantId;

    private Integer pageSize;

    private Integer pageNum;
    /**
     * 物理仓类型(1-真实,0-虚拟)
     */
    private Integer warehouseType;

    private String warehouseNo;

    private String warehouseName;

}
