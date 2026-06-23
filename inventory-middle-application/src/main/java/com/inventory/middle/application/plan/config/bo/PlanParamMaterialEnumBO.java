package com.inventory.middle.application.plan.config.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PlanParamMaterialEnumBO implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<EnumItem> planMaterialParamPlanTypeList;
    private List<EnumItem> demandStrategyList;
    private List<EnumItem> demandTypeList;
    private List<EnumItem> safetyStockCalculateTypeList;

    @Data
    public static class EnumItem implements Serializable {
        private Integer code;
        private String desc;
    }
}
