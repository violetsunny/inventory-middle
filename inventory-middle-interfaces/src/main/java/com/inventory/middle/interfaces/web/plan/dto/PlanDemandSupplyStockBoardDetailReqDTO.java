package com.inventory.middle.interfaces.web.plan.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhouxinzhong
 * @date 2021/11/5 16:44
 */
@Data
public class PlanDemandSupplyStockBoardDetailReqDTO implements Serializable {

    private static final long serialVersionUID = -2520288035705436982L;

    @Schema(description = "逻辑仓编码", required = true)
    private String logicalPlantNo;

    @Schema(description = "物料编码", required = true)
    private String materialCode;
}
