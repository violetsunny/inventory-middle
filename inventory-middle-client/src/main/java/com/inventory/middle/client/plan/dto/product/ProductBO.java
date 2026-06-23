package com.inventory.middle.client.plan.dto.product;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 产品域BO
 *
 * @author Danny.Lee (migrated from scm-plan-management)
 * @date 2022/4/28
 */
@Data
@ToString
public class ProductBO implements Serializable {

    private static final long serialVersionUID = 8078487271302366186L;

    /**
     * id
     */
    private Long id;

    /**
     * 产品名称
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
