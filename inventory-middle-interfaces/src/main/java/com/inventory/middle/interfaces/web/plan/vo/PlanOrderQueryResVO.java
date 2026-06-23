package com.inventory.middle.interfaces.web.plan.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PlanOrderQueryResVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private PlanOrderBaseInfoVO baseInfo;
    private PlanOrderMaterialInfoVO materialInfo;
    private String demandInfo;
}
