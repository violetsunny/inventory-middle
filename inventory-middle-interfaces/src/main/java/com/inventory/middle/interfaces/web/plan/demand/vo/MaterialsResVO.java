package com.inventory.middle.interfaces.web.plan.demand.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
public class MaterialsResVO implements Serializable {
    private static final long serialVersionUID = 5340656041132644782L;

    @Schema(description = "物料列表")
    private List<MaterialResultVO> materialList;
}
