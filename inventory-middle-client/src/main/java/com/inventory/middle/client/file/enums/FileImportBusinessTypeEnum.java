package com.inventory.middle.client.file.enums;

import lombok.Getter;

/**
 * 文件导入处理的业务类型标识枚举
 *
 * @author hjs
 * @date 2022/5/5
 */
@Getter
public enum FileImportBusinessTypeEnum {


    CITY_GAS_INVENTORY_IMPORT("CITY_GAS_INVENTORY_IMPORT", "城燃库存Excel批量导入", "fileImportCoreServiceCityGas"),

    ;
    String code;
    String description;
    String coreServiceBean;

    FileImportBusinessTypeEnum(String code, String description, String coreServiceBean) {
        this.code = code;
        this.description = description;
        this.coreServiceBean = coreServiceBean;
    }

    public static FileImportBusinessTypeEnum getByCode(String code) {
        for (FileImportBusinessTypeEnum e : FileImportBusinessTypeEnum.values()) {
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
