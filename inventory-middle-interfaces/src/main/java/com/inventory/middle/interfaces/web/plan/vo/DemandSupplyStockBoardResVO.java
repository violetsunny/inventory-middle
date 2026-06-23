package com.inventory.middle.interfaces.web.plan.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhouxinzhong
 * @date 2021/11/5 16:50
 */
@Data
@Schema(description = "物料供需存看板")
public class DemandSupplyStockBoardResVO implements Serializable {

    private static final long serialVersionUID = -8369484036373775699L;

    @Schema(description = "物料编码")
    private String materialCode;

    @Schema(description = "物料名称")
    private String materialDesc;

    @Schema(description = "物料清单(bom单号)")
    private String bomId;

    @Schema(description = "单位")
    private String materialUnit;

    @Schema(description = "逻辑仓编码")
    private String logicalPlantNo;
}
