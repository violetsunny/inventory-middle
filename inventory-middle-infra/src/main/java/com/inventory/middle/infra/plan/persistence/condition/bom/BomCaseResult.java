package com.inventory.middle.infra.plan.persistence.condition.bom;

import lombok.Data;

import java.util.Date;

@Data
public class BomCaseResult {

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
