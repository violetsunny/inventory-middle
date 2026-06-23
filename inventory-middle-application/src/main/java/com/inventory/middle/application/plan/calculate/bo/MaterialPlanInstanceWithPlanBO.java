package com.inventory.middle.application.plan.calculate.bo;

import com.inventory.middle.application.plan.calculate.bo.MaterialPlanInstanceBO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 物料计划实例-包含计划配置信息
 *
 * @author Danny.Lee
 * @date 2021/11/2
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MaterialPlanInstanceWithPlanBO extends MaterialPlanInstanceBO {

    private static final long serialVersionUID = -2827205242920232289L;

    private Integer planType;

    private String planCode;

    private String planVersion;
}
