package com.inventory.middle.client.plan.plan.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhouxinzhong
 * @date 2021/11/5 11:07
 */
@Data
public class DemandSupplyStockBoardResDTO implements Serializable {

    private static final long serialVersionUID = 6984485976055168543L;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 物料名称
     */
    private String materialDesc;

    /**
     * 物料清单(bom单号)
     */
    private String bomId;

    /**
     * 单位
     */
    private String materialUnit;

    /**
     * 逻辑仓编码
     */
    private String logicalPlantNo;
}
