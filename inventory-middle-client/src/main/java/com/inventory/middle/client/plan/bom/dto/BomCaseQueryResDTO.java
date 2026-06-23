package com.inventory.middle.client.plan.bom.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhouxinzhong
 * @date 2021/12/8 16:10
 */
@Data
public class BomCaseQueryResDTO implements Serializable {

    private static final long serialVersionUID = -7469317595887239898L;

    /**
     * BOM id
     */
    private String bomId;

    /**
     * BOM编码
     */
    private String code;

    /**
     * BOM名称
     */
    private String name;

    /**
     * 逻辑仓编码
     */
    private String logicalPlantNo;

    /**
     * 逻辑仓名字
     */
    private String logicalPlantName;

    /**
     * 公司代码
     */
    private String companyCode;

    /**
     * 公司代码
     */
    private String companyName;

    /**
     * BOM类型
     */
    private Integer type;

    /**
     * 母件名称
     */
    private String parentName;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 单位
     */
    private String unit;

    /**
     * BOM状态
     */
    private Integer status;

    /**
     * 创建人
     */
    private String createUserName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
