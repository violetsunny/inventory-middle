package com.inventory.middle.client.file.enums;

import lombok.Getter;

/**
 * 业务逻辑的实现类型
 * @author hjs
 * @date 2022/5/5
 */
@Getter
public enum ImplTypeEnum {

    DEFAULT("DEFAULT", "默认", "fileImportCoreServiceDefault"),
    CITY_GAS("CITY_GAS", "城燃", "fileImportCoreServiceCityGas"),

    ;
    String code;
    String description;
    String coreServiceBean;

    ImplTypeEnum(String code, String description, String coreServiceBean) {
        this.code = code;
        this.description = description;
        this.coreServiceBean = coreServiceBean;
    }

    public static ImplTypeEnum getByCode(String code) {
        for (ImplTypeEnum e : ImplTypeEnum.values()) {
            if (e.code.equalsIgnoreCase(code)) {
                return e;
            }
        }
        return null;
    }

    public static boolean isValidCode(String code) {
        return getByCode(code) != null;
    }
}
