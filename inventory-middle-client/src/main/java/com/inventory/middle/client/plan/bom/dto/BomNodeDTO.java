package com.inventory.middle.client.plan.bom.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author:Vincent.Xiao
 * @date:2021/12/6 14:28
 */
@Data
public class BomNodeDTO implements Serializable {

    private static final long serialVersionUID = 9064597960398199889L;
    /**
     * 主键
     */
    private Long id;

    /**
     * bom单id
     */
    private Long bomCaseId;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 物料描述
     */
    private String materialDesc;

    /**
     * 物料单位
     */
    private String materialUnit;

    /**
     * 物料属性
     */
    private String materialAttr;

    /**
     * 物料规格
     */
    private String materialSpec;

    /**
     * 逻辑仓编码
     */
    private String logicalPlantNo;

    /**
     * 逻辑仓名称
     */
    private String logicalPlantName;

    /**
     * 子件数量（保留两位小数点*100//?）
     */
    private Long amount;

    /**
     * 节点类型
     */
    private Integer type;

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * node状态
     */
    private Integer status;
}
