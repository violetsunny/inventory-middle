package com.inventory.middle.client.plan.bom.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: BOM清单BO
 * @date 2021/12/6 15:59
 */
@Data
public class BomCaseDTO implements Serializable {

    private static final long serialVersionUID = -8847619902856638606L;
    /**
     * 主键Id
     */
    private Long id;

    /**
     * bom编码
     */
    private String code;

    /**
     * bom单名称
     */
    private String name;

    /**
     * 公司编码
     */
    private String companyCode;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 逻辑仓编码
     */
    private String logicalPlantNo;

    /**
     * 逻辑仓名称
     */
    private String logicalPlantName;

    /**
     * bom单类型（枚举定义）
     */
    private Integer type;

    /**
     * 状态（枚举定义）
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

}
