package com.inventory.middle.infra.persistence.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author hjs
 * @date 2022/5/5
 */
@Data
public class ListFileImportRecordParam {

    /**
     * 租户id
     */
    private String tenantId;
    /**
     * 文件处理的业务类型
     */
    private String businessType;
    /**
     * 处理状态
     */
    private String processStatus;
    /**
     * 附加字段
     */
    private String extField;
    /**
     * 创建日期，开始
     */
    private Date createDateStart;
    /**
     * 创建日期，结束
     */
    private Date createDateEnd;
}
