package com.inventory.middle.infra.persistence.entity;

import lombok.Data;

import java.util.Date;

@Data
public class FileImportLineRecordDo {
    private Long id;

    private String tenantId;

    private Long fileRecordId;

    private String processStatus;

    private String processMessage;

    private Date createTime;

    private String creatorId;

    private String creator;

    private Date updateTime;

    private String updatorId;

    private String updator;

    private Integer deleted;

    private String lineDetail;
}