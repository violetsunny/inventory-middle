package com.inventory.middle.client.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 接口调用应用服务方标识枚举
 *
 * @author vincent.li
 * @date 2021/10/19
 */
@AllArgsConstructor
@Getter
public enum AppKeyEnum {

    /**
     * 库存域-BFF服务
     */
    INVENTORY_CENTER_BFF("inventory-center-bff", "库存域-BFF服务"),
    /**
     * 采购域-采购服务
     */
    SRM_PURCHASE("srm-purchase", "采购域-采购服务"),
    /**
     * 计划协同-BFF服务
     */
    SCM_PLAN("scm-plan", "计划协同BFF"),
    /**
     * 计划协同-计划管理服务
     */
    SCM_PLAN_MANAGEMENT("scm-plan-management", "计划管理服务"),
    /**
     * 泛能运维中台域-泛能备品备件服务
     */
    FNW_TRADE_SPARE_PART("fnw-trade-spare-part", "泛能备品备件");


    /**
     * 编码
     */
    String code;
    /**
     * 描述
     */
    String desc;

    /**
     * 是否是接入的合法appKey
     * @param appKey 接口调用应用服务方标识
     * @return
     */
    public static Boolean isExist(String appKey) {
        return Arrays.stream(AppKeyEnum.values()).anyMatch(v -> v.getCode().equals(appKey));
    }

    /**
     * 是否是IMP
     * @param appKey 接口调用应用服务方标识
     * @return
     */
    public static Boolean isImp(String appKey) {
        return INVENTORY_CENTER_BFF.getCode().equals(appKey);
    }
}
