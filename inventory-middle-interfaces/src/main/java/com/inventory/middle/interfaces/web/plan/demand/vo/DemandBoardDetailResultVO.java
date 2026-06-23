package com.inventory.middle.interfaces.web.plan.demand.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

@Data
@Schema(description = "需求详情")
public class DemandBoardDetailResultVO implements Serializable {
    private static final long serialVersionUID = -7635607548777174622L;

    @Schema(description = "数量")
    private BigDecimal amount;

    @Schema(description = "扩展信息")
    private Map<String, Object> extInfo;
}
