package com.inventory.middle.interfaces.web.plan.demand.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;

@Data
public class MaterialResultVO implements Serializable {
    private static final long serialVersionUID = -527376643694082260L;

    @Schema(description = "物料编码")
    private String materialCode;

    @Schema(description = "ERP物料编码")
    private String externalMaterialCode;

    @Schema(description = "物料描述")
    private String materialDesc;
}
