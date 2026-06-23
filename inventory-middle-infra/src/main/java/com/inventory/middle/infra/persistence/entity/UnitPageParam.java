package com.inventory.middle.infra.persistence.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author holmes
 * @createTime 2022/5/6 上午1:19
 * @description TODO
 */
@Data
public class UnitPageParam {
    /**
     * 主键
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
     * 创建人
     */
    private String creator;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 删除
     */
    private Integer del;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 启用
     */
    private Integer enable;

    /**
     * 精度
     */
    private String accuracy;

}
