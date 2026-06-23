package com.inventory.middle.client.dto.material;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author kanglele
 */
@Data
public class MaterialDocumentItemDTO implements Serializable {

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

    /**
     * MAP
     */
    private MapJournalDataDTO mapJournalData;

    /**
     * 补充信息
     */
    private MaterialExtDataDTO materialExtData;
}