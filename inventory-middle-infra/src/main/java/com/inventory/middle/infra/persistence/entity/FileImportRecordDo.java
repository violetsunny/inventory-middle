package com.inventory.middle.infra.persistence.entity;

import lombok.Data;

import java.util.Date;

@Data
public class FileImportRecordDo {
    private Long id;

    private String tenantId;

    private String businessType;

    private String fileName;

    private String fileUrl;

    private String resultUrl;

    private String processStatus;

    private String processMessage;

    private String extField;

    private Date createTime;

    private String creatorId;

    private String creator;

    private Date updateTime;

    private String updatorId;

    private String updator;

    private Integer deleted;

    private String extInfo;
}