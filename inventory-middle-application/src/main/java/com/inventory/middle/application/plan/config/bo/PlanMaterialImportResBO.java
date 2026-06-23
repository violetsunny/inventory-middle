package com.inventory.middle.application.plan.config.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PlanMaterialImportResBO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer totalCount;
    private Integer failedCount;
    private List<PlanMaterialImportDetailBO> failedDetails;
}
