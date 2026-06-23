package com.inventory.middle.interfaces.web.plan.demand.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
@Schema(description = "独立需求看板数据")
public class DemandBoardDetailResponseVO implements Serializable {
    private static final long serialVersionUID = 75766391294400821L;

    @Schema(description = "物料编码")
    private String materialCode;

    @Schema(description = "ERP物料编码")
    private String externalMaterialCode;

    @Schema(description = "物料描述")
    private String materialDesc;

    @Schema(description = "单位")
    private String materialUnit;

    @Schema(description = "逻辑仓编码")
    private String logicalPlantNo;

    @Schema(description = "需求列表(Map形式,包含数量和拓展信息)")
    private Map<String, DemandBoardDetailResultVO> periodDemandList;
}
