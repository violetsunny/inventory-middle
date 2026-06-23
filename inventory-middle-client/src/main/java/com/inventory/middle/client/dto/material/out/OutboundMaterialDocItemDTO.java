package com.inventory.middle.client.dto.material.out;

import com.inventory.middle.client.dto.material.FinancialDataDTO;
import com.inventory.middle.client.dto.material.MaterialDataDTO;
import com.inventory.middle.client.dto.material.QuantityAndAmountDataDTO;
import com.inventory.middle.client.dto.material.WarehouseDataDTO;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 出库凭证明细行DTO
 * @author vincent.li
 */
@Data
public class OutboundMaterialDocItemDTO implements Serializable {

    /**
     * 物料code
     */
    private String materialCode;
    /**
     * 批次ID
     */
    private String batchNo;
    /**
     * 凭证
     */
    @NotNull(message = "materialData 物料信息标签必须有")
    private MaterialDataDTO materialData;

    /**
     * 仓库信息
     */
    @NotNull(message = "warehouseData 出入库位置标签必须有")
    private WarehouseDataDTO warehouseData;

    /**
     * 数量和金额
     */
    @NotNull(message = "quantityData 数量标签必须有")
    private QuantityAndAmountDataDTO quantityData;

    /**
     * 财务
     */
    private FinancialDataDTO financeData;

}