package com.inventory.middle.interfaces.web.plan.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PlanMaterialLogicalPlantResVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<PlanMaterialLogicalPlantVO> logicalPlantList;
}
