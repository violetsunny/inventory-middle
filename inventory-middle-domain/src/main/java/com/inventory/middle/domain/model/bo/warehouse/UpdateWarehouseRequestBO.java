package com.inventory.middle.domain.model.bo.warehouse;

import lombok.Data;

@Data
public class UpdateWarehouseRequestBO {

    private Long warehouseId;

    private String tenantId;

    private String ownerName;

    private String phone;

    private String address;

    private String province;

    private String city;

    private String region;

    private String remark;

    private String operator;


}
