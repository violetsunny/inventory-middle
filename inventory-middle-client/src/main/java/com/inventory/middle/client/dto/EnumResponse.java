package com.inventory.middle.client.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class EnumResponse<T> implements Serializable {
    /**
     * 枚举的code
     */
    private T code;
    /**
     * 枚举的描述
     */
    private String description;

    /**
     * 符号
     */
    private String mark;
}
