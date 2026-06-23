package com.inventory.middle.client.file.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 文件导入记录BO对象
 * @author hjs
 * @date 2022/5/5
 */
@Data
public class FileImportRecord implements Serializable {

    private Long id;

    private String tenantId;

    private String businessType;

    private String businessTypeDescription;

    private String fileName;

    private String fileUrl;

    private String resultUrl;

    private String processStatus;

    private String processStatusDescription;

    private String processMessage;

    private String extField;

    private Date createTime;

    private String creatorId;

    private String creator;

    private Date updateTime;

    private String updatorId;

    private String updator;

    private String extInfo;

}
