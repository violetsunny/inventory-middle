package com.inventory.middle.interfaces.web.plan.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 导入物料清单详情
 * @date 2021/10/2 9:55
 */
@Data
public class PlanMaterialImportDetailVO implements Serializable {

    private static final long serialVersionUID = 7527942438254235975L;

    @Schema(description = "表格索引")
    private Integer index;

    @Schema(description = "物料名称")
    private String materialCode;

    @Schema(description = "逻辑仓编码")
    private String logicalPlantNo;

    @Schema(description = "失败原因")
    private String failedReason;
}