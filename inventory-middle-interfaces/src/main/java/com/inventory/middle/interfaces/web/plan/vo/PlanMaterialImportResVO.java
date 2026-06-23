package com.inventory.middle.interfaces.web.plan.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 上传物料计划清单 返参
 * @date 2021/10/2 9:53
 */
@Data
public class PlanMaterialImportResVO implements Serializable {

    private static final long serialVersionUID = 299363890220572898L;

    @Schema(description = "导入总数量")
    private Integer totalCount;

    @Schema(description = "导入失败数量")
    private Integer failedCount;

    @Schema(description = "导入失败详情")
    private List<PlanMaterialImportDetailVO> failedDetails;
}