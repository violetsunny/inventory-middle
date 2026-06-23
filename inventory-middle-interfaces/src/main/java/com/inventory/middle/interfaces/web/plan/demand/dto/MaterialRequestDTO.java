package com.inventory.middle.interfaces.web.plan.demand.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;

@Data
public class MaterialRequestDTO implements Serializable {
    private static final long serialVersionUID = -2426986616616035251L;

    @Schema(description = "物料名称")
    private String materialDesc;

    @Schema(description = "逻辑仓编码")
    private String logicalPlantNo;
}
