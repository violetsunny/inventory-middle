package com.inventory.middle.client.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author holmes
 * @createTime 2022/5/7 下午10:18
 * @description TODO
 */
@Data
public class UnitDto implements Serializable {

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
     * 创建人
     */
    private String creator;

    /**
     * 修改人
     */
    private String modifier;


    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 精度
     */
    private String accuracy;
}
