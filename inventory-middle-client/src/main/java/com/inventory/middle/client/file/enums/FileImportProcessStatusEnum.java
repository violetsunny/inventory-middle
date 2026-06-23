package com.inventory.middle.client.file.enums;

import lombok.Getter;

/**
 * 文件导入处理状态的枚举类
 *
 * @author hjs
 * @date 2022/5/5
 */
@Getter
public enum FileImportProcessStatusEnum {

    PROCESSING("0", "处理中"),
    SUCCESS("1", "处理成功"),
    FAILED("2", "处理失败"),
    //对于没有什么中间过程的，可以就用这个状态
    FINISH("9", "结束"),
    ;

    String code;

    String description;

    FileImportProcessStatusEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static FileImportProcessStatusEnum getByCode(String code) {
        for (FileImportProcessStatusEnum e : FileImportProcessStatusEnum.values()) {
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
