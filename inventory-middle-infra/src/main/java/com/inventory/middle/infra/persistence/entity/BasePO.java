package com.inventory.middle.infra.persistence.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @AUTHOR yinglong.chen
 * @DATE: 2019/6/14 9:38
 * @Description
 **/
@Data
public abstract class BasePO implements Serializable {

    private static final long serialVersionUID = 1503586002332344624L;

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
     * 是否删除 0-否；1-是
     */
    private int deleted;

}
