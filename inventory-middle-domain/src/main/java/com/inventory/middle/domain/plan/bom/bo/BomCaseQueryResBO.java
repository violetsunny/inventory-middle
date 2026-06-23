package com.inventory.middle.domain.plan.bom.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * BOM查询结果BO
 */
@Data
public class BomCaseQueryResBO implements Serializable {

    private static final long serialVersionUID = 3711265926797526785L;

    private String bomId;
    private String code;
    private String name;
    private String logicalPlantNo;
    private String logicalPlantName;
    private String companyCode;
    private String companyName;
    private Integer type;
    private String parentName;
    private String materialCode;
    private String unit;
    private Integer status;
    private String createUserName;
    private Date createTime;
    private Date updateTime;
}
