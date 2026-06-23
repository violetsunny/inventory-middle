package com.inventory.middle.client.plan.plan.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhouxinzhong
 * @date 2021/11/5 14:06
 */
@Data
public class DemandSupplyStockBoardDetailDataDTO implements Serializable {

    private static final long serialVersionUID = -1357615242521705518L;

    /**
     * 	日期
     */
    private Date planDate;

    /**
     * 计划元素类型（1：上期末预计库存，2：客户独立需求，3：计划独立需求，4：BOM相关需求，5：计划订单，6：采购申请，7：采购订单，8：交运单）
     */
    private Integer planElementType;

    /**
     * 单据
     */
    private String voucherNo;

    /**
     * 	行号
     */
    private String documentNo;

    /**
     * 异常码
     */
    private String exceptionCode;

    /**
     * 需求量
     */
    private Long demandAmount;

    /**
     * 供给量
     */
    private Long supplyAmount;

    /**
     * 可用数量
     */
    private Long availableAmount;
}
