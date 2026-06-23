package com.inventory.middle.client.material.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author holmes
 * @createTime 2022/5/6 上午12:54
 * @description TODO
 */
@Data
public class UnitDTO implements Serializable {

    private static final long serialVersionUID = -5724487566000004434L;
    /**
     * 单位id
     */
    private Long id;

    /**
     * 单位
     */
    private String unit;

    /**
     * 单位名称
     */
    private String unitName;

    /**
     * 描述
     */
    private String description;

    /**
     * 业务编码
     */
    private String bizCode;

    /**
     * 启用
     */
    private Integer enable;

    /**
     * 精度
     */
    private String accuracy;



}
