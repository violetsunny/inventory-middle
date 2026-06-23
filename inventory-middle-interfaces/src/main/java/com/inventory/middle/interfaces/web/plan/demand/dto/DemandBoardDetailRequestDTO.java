package com.inventory.middle.interfaces.web.plan.demand.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class DemandBoardDetailRequestDTO implements Serializable {
    private static final long serialVersionUID = 7002684468841483740L;

    @Schema(description = "逻辑仓编码")
    private String logicalPlantNo;

    @Schema(description = "物料编码列表")
    private List<String> materialCodeList;

    @Schema(description = "ERP编码列表")
    private List<String> externalMaterialCodeList;

    @Schema(description = "开始时间")
    private Date beginTime;

    @Schema(description = "结束时间")
    private Date endTime;

    @Schema(description = "页码", required = true)
    private Integer pageNum;

    @Schema(description = "页大小", required = true)
    private Integer pageSize;
}
