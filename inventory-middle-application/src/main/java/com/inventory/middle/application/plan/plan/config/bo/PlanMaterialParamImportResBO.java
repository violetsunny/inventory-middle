package com.inventory.middle.application.plan.plan.config.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PlanMaterialParamImportResBO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer totalCount;
    private Integer failedCount;
    private List<PlanMaterialParamImportDetailBO> failedDetails;
}
