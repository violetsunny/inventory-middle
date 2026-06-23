package com.inventory.middle.infra.persistence.entity;

import lombok.Data;

import java.util.Date;

@Data
public class CodeDo {

    /**
     * 主键id
     */
    private Long id;
    /**
     * 业务编号
     */
    private String businessNo;
    /**
     * 产生码记录的源单号
     */
    private String sourceNo;
    /**
     * 内部唯一码
     */
    private String innerCode;

    /**
     * 码记录的类型
     */
    private String type;
    /**
     * 码记录的值
     */
    private String code;
    /**
     * 码记录的发布者
     */
    private String publisher;
    /**
     * 码记录的前一任持有者
     */
    private String preOwner;
    /**
     * 码记录的当前持有者
     */
    private String currentOwner;
    /**
     * 码记录的状态
     */
    private String status;
    /**
     * 码记录的扩展字段
     */
    private String extendField1;
    /**
     * 码记录的扩展字段
     */
    private String extendField2;
    /**
     * 码记录的扩展参数
     */
    private String extendParam;
    /**
     * 生效时间
     */
    private Date activeTime;

    /**
     * 创建人id
     */
    private String creatorId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新人id
     */
    private String updatorId;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 是否删除
     */
    private Long deleted;
}