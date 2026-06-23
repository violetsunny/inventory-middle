package com.inventory.middle.client.plan.config.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhouxinzhong
 * @date 2021/11/5 14:15
 */
@Data
public class DemandSupplyStockBoardDetailResDTO implements Serializable {

    private static final long serialVersionUID = 562349992052325811L;

    /**
     * 看板数据
     */
    private List<DemandSupplyStockBoardDetailDataDTO> boardData;

    /**
     * 图表数据
     */
    private List<DemandSupplyStockBoardDetailChartDTO> chartData;
}
