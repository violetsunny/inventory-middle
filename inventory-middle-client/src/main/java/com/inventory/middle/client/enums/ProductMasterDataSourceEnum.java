package com.inventory.middle.client.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 产品主数据来源枚举
 *
 * @author vincent.li
 * @date 2022/05/28
 */
@ToString
@AllArgsConstructor
@Getter
public enum ProductMasterDataSourceEnum implements Serializable {

    /**
     * 主数据-产品中心
     */
    PRODUCT_CENTER("product_center", "主数据-产品中心"),
    /**
     * 供应链-库存中心-物料主数据
     */
    SCM_INVENTORY_CENTER_M_DATA("scm_inventory_center_m_data", "供应链-库存中心-物料主数据");


    /**
     * 编码
     */
   private String code;
    /**
     * 描述
     */
   private String desc;

    /**
     * 是否是合法source
     * @param source 来源
     * @return
     */
    public static Boolean isExist(String source) {
        return Arrays.stream(ProductMasterDataSourceEnum.values()).anyMatch(v -> v.getCode().equals(source));
    }

    /**
     * 是否使用产品中心
     * @param source 数据源
     * @return
     */
    public static Boolean isUseProductCenter(String source) {
        return PRODUCT_CENTER.getCode().equals(source);
    }

    /**
     * 是否使用供应链库存中心物料主数据
     * @param source 数据源
     * @return
     */
    public static Boolean isUseScmInventoryCenterMData(String source) {
        return SCM_INVENTORY_CENTER_M_DATA.getCode().equals(source);
    }

    public static Boolean isUseScmInventoryCenterMData(ProductMasterDataSourceEnum sourceEnum) {
        return SCM_INVENTORY_CENTER_M_DATA.equals(sourceEnum);
    }


}
