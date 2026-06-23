package com.inventory.middle.domain.model.bo.transit;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.inventory.middle.domain.model.enums.StockTypeEnum;
import com.inventory.middle.client.enums.TransferTransitTypeEnum;
import lombok.Data;

/**
 * @description 在途库存 -> 在库库存请求
 * @author dongguo.tao
 * @date 2021-09-27 17:58:09
 */

@Data
public class TransferTransitMaterialBO implements Serializable {

    private static final long serialVersionUID = -8911494333617454228L;
    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 逻辑仓id
     */
    private Long logicalPlantId;

    /**
     * 存储地点id
     */
    private Long locationId;

    /**
     * 入库数量
     */
    private BigDecimal inBoundQuantity;

    /**
     * 库存类型
     * @see StockTypeEnum
     */
    private Integer stockType;

    /**
     * 转移类型
     * @see TransferTransitTypeEnum
     */
    private Integer transferType;

}
