package com.inventory.middle.interfaces.web.plan.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhouxinzhong
 * @date 2021/11/5 17:07
 */
@Data
@Schema(description = "供需存看板详情图表")
public class DemandSupplyStockBoardDetailChartVO implements Serializable {

    private static final long serialVersionUID = 6527285171511988619L;

    @Schema(description = "需求量")
    private Long demandAmount;

    @Schema(description = "供给量")
    private Long supplyAmount;
}
