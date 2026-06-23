package com.inventory.middle.interfaces.web.plan.vo;

import com.inventory.middle.client.plan.dto.inventory.LogicalPlant;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 物料查询返参
 * @date 2021/10/18 17:27
 */
@Data
@Builder
public class MaterialInfoQueryResVO implements Serializable {

    private static final long serialVersionUID = 4045664992864342823L;

    @Schema(description = "物料编码")
    private String materialCode;

    @Schema(description = "物料名称")
    private String materialName;

    @Schema(description = "物料单位")
    private String unit;

    @Schema(description = "物料所在逻辑仓")
    private List<LogicalPlant> logicalPlantList;
}