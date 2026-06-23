package com.inventory.middle.interfaces.web.plan.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhouxinzhong
 * @date 2021/11/5 16:18
 */
@Data
public class PlanDemandSupplyStockBoardReqDTO implements Serializable {

    private static final long serialVersionUID = -7875597691662618772L;

    @Schema(description = "逻辑仓编码", required = true)
    private String logicalPlantNo;

    @Schema(description = "物料编码")
    private String materialCode;

    @Schema(description = "页码", required = true)
    private Integer pageNum;

    @Schema(description = "页大小", required = true)
    private Integer pageSize;
}
