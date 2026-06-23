package com.inventory.middle.interfaces.web.plan.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 导入结果
 * @date 2021/9/26 14:10
 */
@Data
public class PlanMaterialParamImportResVO implements Serializable {

    private static final long serialVersionUID = 3442766126799131944L;

    @Schema(description = "导入总数量")
    private Integer totalCount;

    @Schema(description = "导入失败数量")
    private Integer failedCount;

    @Schema(description = "导入失败详情")
    private List<PlanMaterialParamImportDetailVO> failedDetails;

}
