package com.inventory.middle.interfaces.web.plan.demand.dto;

import com.inventory.middle.client.plan.demand.dto.DemandPlanSelectReqDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 需求计划分页查询请求 DTO（interfaces 层，含分页参数）
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DemandPlanSelectRequestDTO extends DemandPlanSelectReqDTO {

    @Schema(description = "页码", required = true)
    private Integer pageNum = 1;

    @Schema(description = "页大小", required = true)
    private Integer pageSize = 20;
}
