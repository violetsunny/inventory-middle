package com.inventory.middle.domain.model.bo.transit;

import java.io.Serializable;
import java.util.List;

import com.inventory.middle.domain.model.enums.MaterialDocRefOrderTypeEnum;
import lombok.Data;

/**
 * @description 在途库存 -> 在库库存请求
 * @author dongguo.tao
 * @date 2021-09-27 17:58:09
 */

@Data
public class TransferTransitStockRequestBO implements Serializable {

    private static final long serialVersionUID = -4653834907442568719L;

    /**
     * 租户id
     */
    private String tenantId;

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
     * 物料相关信息
     */
    private List<TransferTransitMaterialBO> materialBOList;


}
