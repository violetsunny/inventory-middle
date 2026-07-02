package com.inventory.middle.domain.repository;

import lombok.Data;

import java.util.Date;

@Data
public class FileImportRecordQueryParam {

    private String tenantId;

    private String businessType;

    private String processStatus;

    private String extField;

    private Date createDateStart;

    private Date createDateEnd;
}
