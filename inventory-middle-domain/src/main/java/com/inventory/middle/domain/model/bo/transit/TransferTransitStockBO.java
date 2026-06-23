package com.inventory.middle.domain.model.bo.transit;

import java.io.Serializable;
import java.math.BigDecimal;

import com.inventory.middle.domain.model.enums.MaterialDocRefOrderTypeEnum;
import com.inventory.middle.domain.model.enums.StockTypeEnum;
import com.inventory.middle.client.enums.TransferTransitTypeEnum;
import lombok.Data;

/**
 * @description 在途库存 -> 在库库存请求
 * @author dongguo.tao
 * @date 2021-09-27 17:58:09
 */

@Data
public class TransferTransitStockBO implements Serializable {


    private static final long serialVersionUID = 495587419478801661L;

    /**
     * 租户id
     */
    private String tenantId;

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
     * 来源单据类型
     * @see MaterialDocRefOrderTypeEnum
     */
    private Integer sourceOrderType;

    /**
     * 来源单据编号
     */
    private String sourceOrderNo;

    /**
     * 交运单号
     */
    private String deliveryNo;

    /**
     * 操作人id
     */
    private String operatorId;

    /**
     * 转移类型
     * @see TransferTransitTypeEnum
     */
    private Integer transferType;

}
