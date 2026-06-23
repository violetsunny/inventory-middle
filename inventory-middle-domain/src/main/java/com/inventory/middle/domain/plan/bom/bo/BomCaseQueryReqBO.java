package com.inventory.middle.domain.plan.bom.bo;

import lombok.Data;

import java.util.List;

/**
 * BOM查询请求BO
 */
@Data
public class BomCaseQueryReqBO {

    private String code;
    private String name;
    private List<String> logicalPlantNos;
    private String companyName;
    private Integer type;
    private String materialCode;
    private Integer status;
    private Integer pageNum;
    private Integer pageSize;
    private String tenantId;
}
