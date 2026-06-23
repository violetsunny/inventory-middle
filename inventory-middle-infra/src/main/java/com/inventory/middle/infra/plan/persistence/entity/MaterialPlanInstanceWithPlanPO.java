package com.inventory.middle.infra.plan.persistence.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author Danny.Lee
 * @date 2021/11/2
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MaterialPlanInstanceWithPlanPO extends MaterialPlanInstancePO {

    private Integer planType;

    private String planCode;

    private String planVersion;
}
