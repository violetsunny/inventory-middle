package com.inventory.middle.infra.persistence.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @description 在途库存查询
 * @author dongguo.tao
 * @date 2021-09-27 17:58:09
 */

@Data
public class TransferTransitStockDo implements Serializable {


    private static final long serialVersionUID = 1362194814878251387L;
    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 物料编码
     */
    private String materialCode;
    /**
     * 物料凭证ID
     */
    private Long materialDocId;
    /**
     * 逻辑仓id
     */
    private Long logicalPlantId;

    /**
     * 存储地点id
     */
    private Long locationId;

    /**
     * 库存类型
     */
    private Integer stockType;

    /**
     * 来源单据类型
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
     * 最早可用库存开始时间（包含）
     */
    private Date etaStartTime;

    /**
     * 最早可用库存结束时间（不包含）
     */
    private Date etaEndTime;

}
