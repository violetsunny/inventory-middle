package com.inventory.middle.interfaces.web.plan.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author caosheng
 * @title: QueryPlanTransferLogicalPlantNoReqDTO
 * @projectName scm-plan-bff
 * @description: 获取调拨源头仓请求类
 * @date 2021/11/25 10:26
 */
@Data
public class QueryPlanTransferLogicalPlantNoReqDTO implements Serializable {

    private static final long serialVersionUID = 2890685665581665873L;

    @Schema(description = "物料编码", required = true)
    private String materialCode;

}
