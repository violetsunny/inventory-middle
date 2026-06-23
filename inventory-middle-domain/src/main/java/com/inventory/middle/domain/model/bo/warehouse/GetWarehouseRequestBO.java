package com.inventory.middle.domain.model.bo.warehouse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetWarehouseRequestBO {

    private String tenantId;

    private Long warehouseId;

}
