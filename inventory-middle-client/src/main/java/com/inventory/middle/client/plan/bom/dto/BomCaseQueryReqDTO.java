package com.inventory.middle.client.plan.bom.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhouxinzhong
 * @date 2021/12/8 14:54
 */
@Data
public class BomCaseQueryReqDTO implements Serializable {

    private static final long serialVersionUID = -3019428194641079492L;

    /**
     * BOM编码
     */
    private String code;

    /**
     * BOM名称
     */
    private String name;

    /**
     * 逻辑仓(多选)
     */
    private List<String> logicalPlantNos;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * BOM类型
     */
    private Integer type;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * BOM状态
     */
    private Integer status;

    /**
     * 页码
     */
    private Integer pageNum;

    /**
     * 页大小
     */
    private Integer pageSize;

    /**
     * 租户id
     */
    private String tenantId;
}
