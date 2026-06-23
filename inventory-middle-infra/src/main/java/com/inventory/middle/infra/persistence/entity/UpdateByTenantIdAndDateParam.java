package com.inventory.middle.infra.persistence.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author hjs
 * @date 2022/5/5
 */
@Data
public class UpdateByTenantIdAndDateParam {


    private String extField;

    private Date updateTime;

    private String updatorId;

    private String updator;


    /**------------下面为条件----------*/
    /**
     * 租户
     */
    private String tenantId;
    /**
     * 创建日期，开始
     */
    private Date createDateStart;
    /**
     * 创建日期，结束
     */
    private Date createDateEnd;
}
