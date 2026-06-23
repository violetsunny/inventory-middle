package com.inventory.middle.application.plan.config.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PlanPageResBO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String planCode;
    private String planDesc;
    private Integer planType;
    private String planTypeDesc;
    private Date updateTime;
    private String updateTimeStr;
    private String operatorName;
    private Integer status;
    private String statusDesc;
    private Integer coverMaterialType;
    private Integer exported;
    private Integer relatedBom;
}
