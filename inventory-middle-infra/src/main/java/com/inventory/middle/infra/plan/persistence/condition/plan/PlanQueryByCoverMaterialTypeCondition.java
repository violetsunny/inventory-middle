package com.inventory.middle.infra.plan.persistence.condition.plan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanQueryByCoverMaterialTypeCondition {
    private String tenantId;
    private Integer coverMaterialType;
}
