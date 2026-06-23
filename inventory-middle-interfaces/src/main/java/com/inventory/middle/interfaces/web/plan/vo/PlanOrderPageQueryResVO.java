package com.inventory.middle.interfaces.web.plan.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PlanOrderPageQueryResVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String orderNo;
    private String materialCode;
    private String materialDesc;
    private String externalMaterialCode;
    private String logicalPlantNo;
    private String logicalPlantName;
    private Integer planOrderQuantity;
    private Long id;
    private Integer issueQuantity;
    private Date updateTime;
    private Integer createType;
    private String createUser;
    private Integer status;
    private String planCode;
}
