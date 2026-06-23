package com.inventory.middle.interfaces.web.plan.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhouxinzhong
 * @date 2021/11/5 17:49
 */
@Data
@Schema(description = "供需存看板详情")
public class DemandSupplyStockBoardDetailResVO implements Serializable {

    private static final long serialVersionUID = 2428963726761355369L;

    @Schema(description = "看板数据")
    private List<DemandSupplyStockBoardDetailDataVO> boardData;

    @Schema(description = "图表数据")
    private List<DemandSupplyStockBoardDetailChartVO> chartData;
}
