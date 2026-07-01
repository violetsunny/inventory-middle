package com.inventory.middle.domain.plan.common.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 计划域-产品/物料BO
 * <p>
 * 迁移自: com.enn.plan.management.support.product.bo.ProductBO
 * </p>
 *
 * @author Danny.Lee (original)
 */
@Data
public class PlanProductBO implements Serializable {

    private static final long serialVersionUID = 8078487271302366186L;

    /**
     * id
     */
    private Long id;

    /**
     * 产品/物料名称
     */
    private String name;

    /**
     * 物料编码
     */
    private String code;

    /**
     * 外部物料编码
     */
    private String outMaterialCode;

    /**
     * 产品描述
     */
    private String desc;

    /**
     * 单位
     */
    private String unit;
}
