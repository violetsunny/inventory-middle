package com.inventory.middle.client.dto.material.in;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.inventory.middle.client.dto.material.*;

import lombok.Data;

/**
 * 入库凭证明细行DTO
 * @author vincent.li
 */
@Data
public class InboundMaterialDocItemDTO implements Serializable {

    /**
     * 物料code
     */
    private String materialCode;
    /**
     * 批次ID
     */
    private String batchNo;
    /**
     * 物料信息标签
     */
    @NotNull(message = "materialData 物料信息标签必须有")
    private MaterialDataDTO materialData;

    /**
     * 出入库位置标签
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

    /**
     * 补充信息
     */
    private MaterialExtDataDTO materialExtData;

}